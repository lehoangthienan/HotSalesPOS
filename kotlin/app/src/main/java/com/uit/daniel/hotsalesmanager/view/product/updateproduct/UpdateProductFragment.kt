package com.uit.daniel.hotsalesmanager.view.product.updateproduct

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
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.utils.*
import kotlinx.android.synthetic.main.fragment_update_product.*
import java.io.ByteArrayOutputStream
import java.util.*

class UpdateProductFragment : Fragment() {

    private lateinit var updateProductViewModel: UpdateProductViewModel
    private lateinit var productResponse: ProductResponse
    private var productId = ""
    private var imageUrl = ""
    private lateinit var bottomSheetDialogUploadImage: BottomSheetDialog
    private val intentUtils = IntentUtils()
    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.getReferenceFromUrl("gs://hotsalesmanager-fef89.appspot.com")
    private lateinit var userManagerUtil: UserManagerUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update_product, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addControls()
        getProductId()
        getProductDetail()
        addEvents()
    }

    private fun addControls() {
        bottomSheetDialogUploadImage = BottomSheetDialog(activity)
        bottomSheetDialogUploadImage.setContentView(R.layout.bottomsheetdialog_update_avatar_profile)
        bottomSheetDialogUploadImage.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            setLatLagOfProduct()
            activity.finish()
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvCheckFinish.setOnClickListener {
            isFullField()
        }
        ivProductImage.setOnClickListener {
            showBottomDialog()
        }
    }

    private fun isFullField() {
        if (etName.text.toString().isNullOrBlank() ||
            etPrice.text.toString().isNullOrBlank() ||
            etDiscount.text.toString().isNullOrBlank() ||
            etContent.text.toString().isNullOrBlank() ||
            imageUrl.isNullOrBlank()
        ) ToastSnackBar.showSnackbar("Please enter full information before proceeding.", view, activity)
        else {
            updateProduct()
        }
    }

    @SuppressLint("CheckResult")
    private fun updateProduct() {
        updateProductViewModel.updateProductObservable().subscribe { check ->
            if (check) {
                activity.finish()
                activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            } else ToastSnackBar.showSnackbar("Update product fail!", view, activity)
        }
        setProductResponse()
        updateProductViewModel.updateProduct(productId, productResponse)
    }

    private fun setProductResponse() {
        productResponse.result!![0].name = etName.text.toString()
        productResponse.result!![0].price = etPrice.text.toString().toInt()
        productResponse.result!![0].discount = etDiscount.text.toString().toInt()
        productResponse.result!![0].content = etContent.text.toString()
        productResponse.result!![0].image = imageUrl
    }

    @SuppressLint("CheckResult")
    private fun getProductDetail() {
        updateProductViewModel.productObservable().subscribe { response ->
            productResponse = response
            initView()
        }
        updateProductViewModel.product(productId)
    }

    private fun initView() {
        if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                getVisibilityView(false)

        etName.setText(productResponse.result!![0].name)
        etPrice.setText(productResponse.result!![0].price.toString())
        etDiscount.setText(productResponse.result!![0].discount.toString())
        etContent.setText(productResponse.result!![0].content)
        try {
            ivProductImage?.let {
                Glide.with(activity)
                    .asBitmap()
                    .load(productResponse.result?.get(0)?.image)
                    .into(it)
            }
        } catch (e: Exception) {
        }
        imageUrl = productResponse.result?.get(0)?.image.toString()
    }

    private fun getProductId() {
        productId = activity?.intent?.getStringExtra("ID") ?: ""
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateProductViewModel = UpdateProductViewModel(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
    }

    private fun startCameraIntent() {
        val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            startCameraActivity()
        }
    }

    private fun setLatLagOfProduct() {
        userManagerUtil.setLat(0.0)
        userManagerUtil.setLng(0.0)
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
        if (data?.extras?.get("data") == null){
            if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                    getVisibilityView(false)
            return
        }

        if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                getVisibilityView(true)

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
        if (data?.data == null){
            if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                    getVisibilityView(false)
            return
        }

        if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                getVisibilityView(true)

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