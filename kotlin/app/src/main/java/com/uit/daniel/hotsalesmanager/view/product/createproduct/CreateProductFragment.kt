package com.uit.daniel.hotsalesmanager.view.product.createproduct

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.*
import com.uit.daniel.hotsalesmanager.view.location.searchaddresslocation.SearchAddressLocationActivity
import kotlinx.android.synthetic.main.fragment_create_product.*
import java.io.ByteArrayOutputStream
import java.util.*

class CreateProductFragment : Fragment() {

    private lateinit var createGroupViewModel: CreateGroupViewModel
    private lateinit var userManagerUtil: UserManagerUtil
    private var imageUrl = ""
    private lateinit var bottomSheetDialogUploadImage: BottomSheetDialog
    private val intentUtils = IntentUtils()
    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.getReferenceFromUrl("gs://hotsalesmanager-fef89.appspot.com")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        addControls()
        addEvents()
    }

    private fun initView() {
        if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                getVisibilityView(false)
    }

    private fun addControls() {
        bottomSheetDialogUploadImage = BottomSheetDialog(activity)
        bottomSheetDialogUploadImage.setContentView(R.layout.bottomsheetdialog_update_avatar_profile)
        bottomSheetDialogUploadImage.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvCheckFinish.setOnClickListener {
            isFullField()
        }
        tvAddLocation.setOnClickListener {
            startSearchLocationActivity()
        }
        ivProductImage.setOnClickListener {
            showBottomDialog()
        }
    }

    private fun startCameraIntent() {
        val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            startCameraActivity()
        }
    }

    private fun startCameraActivity() {
        try {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
                this.startActivityForResult(takePictureIntent, Constant.REQUEST_CAMERA)
            }
        } catch (e: Exception) {
        }
    }

    private fun showBottomDialog() {
        showBottomSheetDialogUploadImage()
    }

    private fun showBottomSheetDialogUploadImage() {
        bottomSheetDialogUploadImage.show()
        bottomSheetDialogUploadImage.findViewById<RelativeLayout>(R.id.takePhotoFromCamera)?.setOnClickListener {
            try {
                startCameraIntent()
            } catch (e: Exception) {
            }
        }

        bottomSheetDialogUploadImage.findViewById<RelativeLayout>(R.id.takePhotoFromLibrary)?.setOnClickListener {
            try {
                startActivityForResult(intentUtils.intentActionPick(), Constant.REQUEST_GALLERY)
            } catch (e: Exception) {
            }
        }
    }

    private fun startSearchLocationActivity() {
        val intent = Intent(activity, SearchAddressLocationActivity::class.java)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    @SuppressLint("CheckResult")
    private fun isFullField() {
        if (etName.text.toString().isNullOrBlank() ||
            etPrice.text.toString().isNullOrBlank() ||
            etDiscount.text.toString().isNullOrBlank() ||
            etContent.text.toString().isNullOrBlank() ||
            imageUrl.isNullOrBlank()
        ) ToastSnackBar.showSnackbar("Please enter full information before proceeding.", view, activity)
        else {
            isLocation()
        }
    }

    private fun isLocation() {
        if (userManagerUtil.getLat() == 0.0 || userManagerUtil.getLng() == 0.0) ToastSnackBar.showSnackbar(
            "Please input your product location before proceeding.",
            view,
            activity
        )
        else createProduct()
    }

    @SuppressLint("CheckResult")
    private fun createProduct() {
        createGroupViewModel.createProductObservable().subscribe { check ->
            if (check) {
                setLatLagOfProduct()
                activity.finish()
                activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
        createGroupViewModel.createProduct(
            etName.text.toString(),
            etPrice.text.toString().toInt(),
            etDiscount.text.toString().toInt(),
            spCategory.selectedItemPosition,
            etContent.text.toString(),
            imageUrl
        )
    }

    private fun setLatLagOfProduct() {
        userManagerUtil.setLat(0.0)
        userManagerUtil.setLng(0.0)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        createGroupViewModel = CreateGroupViewModel(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                getVisibilityView(true)

        when (requestCode) {
            Constant.REQUEST_GALLERY -> {
                setImageForGallery(data)
            }
            Constant.REQUEST_CAMERA -> {
                setImageForCamera(data)
            }
        }
    }

    private fun setImageForCamera(data: Intent?) {
        if (data?.extras?.get("data") == null) return
        val bitmap = data.extras?.get("data") as Bitmap

        updateImageCameraToSever(bitmap)

        Glide.with(this)
            .asBitmap()
            .load(bitmap)
            .into(ivProductImage)
        bottomSheetDialogUploadImage.dismiss()
    }

    private fun updateImageCameraToSever(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val childRef = storageRef.child("images/" + UUID.randomUUID().toString())
        val uploadTask = childRef.putBytes(data)
        uploadTask.addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
            override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot?) {
                childRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                    @SuppressLint("CheckResult")
                    override fun onSuccess(uri: Uri?) {
                        imageUrl = uri.toString()

                        if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                                getVisibilityView(false)
                    }
                })
            }
        })
    }

    private fun setImageForGallery(data: Intent?) {
        //Get uri from picture choose from gallery
        val uri = data?.data ?: return
        val projection = arrayOf(android.provider.MediaStore.Images.Media.DATA)

        val cursor = activity.contentResolver.query(uri, projection, null, null, null)
        cursor.moveToFirst()

        val columnIndex = cursor.getColumnIndex(projection[0])
        val filepath = cursor.getString(columnIndex)
        cursor.close()

        val bitmap = BitmapFactory.decodeFile(filepath)
        ivProductImage.setImageBitmap(bitmap)
        bottomSheetDialogUploadImage.dismiss()

        updateImageToSever(uri)
    }

    private fun updateImageToSever(uri: Uri) {
        if (uri != null) {
            val childRef = storageRef.child("images/" + UUID.randomUUID().toString())
            val uploadTask = childRef.putFile(uri)
            uploadTask.addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot?) {
                    childRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        @SuppressLint("CheckResult")
                        override fun onSuccess(uri: Uri?) {
                            imageUrl = uri.toString()

                            if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                                    getVisibilityView(false)
                        }
                    })
                }
            })
        }
    }
}