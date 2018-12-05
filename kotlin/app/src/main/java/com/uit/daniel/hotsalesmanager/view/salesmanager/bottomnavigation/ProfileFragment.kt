package com.uit.daniel.hotsalesmanager.view.salesmanager.bottomnavigation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.*
import com.uit.daniel.hotsalesmanager.view.custom.countrycode.Country
import com.uit.daniel.hotsalesmanager.view.custom.countrycode.CustomAdapter
import com.uit.daniel.hotsalesmanager.view.custom.countrycode.listCodeCountry
import com.uit.daniel.hotsalesmanager.view.salesmanager.SalesManagerViewModel
import com.uit.daniel.hotsalesmanager.view.signin.updateuserphonenumber.UpdatePhoneNumberActivity
import com.uit.daniel.hotsalesmanager.view.signin.updateuserprofile.UpdateUserProfileViewModel
import kotlinx.android.synthetic.main.dialog_permissin_read_write_storage.*
import kotlinx.android.synthetic.main.fragment_navigation_profile.*
import java.io.ByteArrayOutputStream
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var salesManagerViewModel: SalesManagerViewModel
    private lateinit var rxPermissionsSTORAGE: RxPermissions
    private lateinit var bottomSheetDialogUpdateAvatarUser: BottomSheetDialog
    private lateinit var dlPermissionStorage: Dialog
    private val intentUtils = IntentUtils()
    private lateinit var userManagerUtil: UserManagerUtil
    private var phoneNumber: String = ""
    private var phoneNumberVerified: String = ""
    private var codeCountry: String = ""
    private var userName: String = ""
    private var urlAvatarUser: String = ""
    private var check: Boolean = false
    private lateinit var dlNotInputPhoneNumber: Dialog
    private lateinit var updateUserProfileViewModel: UpdateUserProfileViewModel
    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.getReferenceFromUrl("gs://hotsalesmanager-fef89.appspot.com")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addControls()
        addItemSpinerCodeCountry()
        setFocusInitView()
        setDefaultValueOfSpinner()
        getCodeCountryPhoneNumber()
        getDataUser()
        initView()
        addEvents()

    }

    private fun addControls() {
        dlNotInputPhoneNumber = Dialog(activity)
        dlNotInputPhoneNumber.setContentView(R.layout.dialog_not_input_phone_number)
        dlNotInputPhoneNumber.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        bottomSheetDialogUpdateAvatarUser = BottomSheetDialog(activity)
        bottomSheetDialogUpdateAvatarUser.setContentView(R.layout.bottomsheetdialog_update_avatar_profile)
        bottomSheetDialogUpdateAvatarUser.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dlPermissionStorage = Dialog(activity)
        dlPermissionStorage.setContentView(R.layout.dialog_permissin_read_write_storage)
        dlPermissionStorage.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun startConfirmPhoneNumberActivity() {
        getCodeCountryPhoneNumber()
        val intent = Intent(activity, UpdatePhoneNumberActivity::class.java)
        startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun getCodeCountryPhoneNumber() {
        spCodeCoutry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                codeCountry = "+84"
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val country: Country = p0?.getItemAtPosition(p2) as Country
                codeCountry = country.code
            }
        }
        if (!etPhoneNumber?.text.toString().isEmpty()) {
            setPhoneNumberInUtils()
        }
    }

    private fun setPhoneNumberInUtils() {
        var phoneNumberMain: String = etPhoneNumber?.text.toString()
        phoneNumberMain = PhoneNumberUtil().replacePhoneNumber(phoneNumberMain)
        phoneNumber = codeCountry + phoneNumberMain
        userManagerUtil.setUserPhoneNumber(phoneNumber)
    }

    private fun getDataUser() {
        userName = userManagerUtil.getUserName()
        urlAvatarUser = userManagerUtil.getUrlkUserAvatar()
        check = userManagerUtil.getCheck()
        phoneNumber = userManagerUtil.getUserPhoneNumber()
        phoneNumberVerified = userManagerUtil.getUserPhoneNumberVerifired()
    }

    private fun setDefaultValueOfSpinner() {
        spCodeCoutry.setSelection(239)
    }

    private fun addItemSpinerCodeCountry() {
        val countries = listCodeCountry(activity)
        val adapteSpinnerCountryCode =
            CustomAdapter(activity, R.layout.fragment_update_user_profile, countries)
        spCodeCoutry?.adapter = adapteSpinnerCountryCode
    }

    private fun setFocusInitView() {
        if (etName?.text?.toString() != null) {
            if (etName?.text?.toString()!!.isEmpty()) {
                etName?.requestFocus()
            } else {
                etPhoneNumber?.requestFocus()
            }
        }
    }

    private fun initView() {

        if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                getVisibilityView(false)

        try {
            if (!userName.isEmpty()) {
                etName.setText(userName)
            }
            if (!urlAvatarUser.isEmpty()) {
                Glide.with(activity)
                    .asBitmap()
                    .load(urlAvatarUser)
                    .into(imgAvatarUpdate)
            }
        } catch (e: Exception) {

        }
    }

    @SuppressLint("CheckResult")
    private fun addEvents() {
        viewEditAvatar.setOnClickListener {
            setPermission()
        }
        tvUpdate.setOnClickListener {
            updateUserName()
            updateUserPhoneNumber()
        }
    }

    private fun updateUserPhoneNumber() {
        if (!etPhoneNumber.text.toString().isNullOrBlank() && etPhoneNumber.text.toString() != userManagerUtil.getUserPhoneNumberVerifired()) {
            startConfirmPhoneNumberActivity()
        }
    }

    @SuppressLint("CheckResult")
    private fun updateUserName() {
        updateUserProfileViewModel.isUpdateUserNameToSever().subscribe { check ->
            if (check) {
                userManagerUtil.setUserName(etName.text.toString())
            }
        }
        if (etName.text.toString() != userManagerUtil.getUserName() || etName.text.toString() != "Hot Sales") updateUserProfileViewModel.updateUserNameToSever(
            userManagerUtil.getUserId(),
            etName.text.toString()
        )
    }

    private fun setPermission() {
        var permissionStorage = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionStorage == PackageManager.PERMISSION_GRANTED) {
            showBottomSheetDialogUpdateAvatarUser()
        } else {
            dlPermissionStorage.show()
            dlPermissionStorage.tvAcceptPermissionStorage?.setOnClickListener {
                dlPermissionStorage.dismiss()
                rxPermissionsSTORAGE
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            //Not permission
                        } else {
                            permissionStorage =
                                    ContextCompat.checkSelfPermission(
                                        activity,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    )
                            showBottomSheetDialogUpdateAvatarUser()
                        }
                        showBottomSheetDialogUpdateAvatarUser()
                    }
            }
            dlPermissionStorage.tvCancelPermissionStorage.setOnClickListener {
                dlPermissionStorage.dismiss()
            }
        }
    }

    private fun showBottomSheetDialogUpdateAvatarUser() {
        bottomSheetDialogUpdateAvatarUser.show()
        bottomSheetDialogUpdateAvatarUser.findViewById<RelativeLayout>(R.id.takePhotoFromCamera)?.setOnClickListener {
            startCameraIntent()
        }

        bottomSheetDialogUpdateAvatarUser.findViewById<RelativeLayout>(R.id.takePhotoFromLibrary)?.setOnClickListener {
            startActivityForResult(intentUtils.intentActionPick(), Constant.REQUEST_GALLERY)
        }
    }

    private fun startCameraIntent() {
        val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            startCameraActivity()
        }
    }

    private fun startCameraActivity() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            this.startActivityForResult(takePictureIntent, Constant.REQUEST_CAMERA)
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
        if (data?.extras?.get("data") == null) return
        val bitmap = data.extras?.get("data") as Bitmap

        updateImageCameraToSever(bitmap)

        Glide.with(this)
            .asBitmap()
            .load(bitmap)
            .into(imgAvatarUpdate)
        bottomSheetDialogUpdateAvatarUser.dismiss()
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
                        updateUserProfileViewModel.isUpdateAvatarToSever().subscribe { check ->
                            if (check) {
                                userManagerUtil.setUserUrlAvatar(uri.toString())
                                if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                                        getVisibilityView(false)
                            }
                        }
                        updateUserProfileViewModel.updateUserAvatarRequest(userManagerUtil.getUserId(), uri.toString())
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
        imgAvatarUpdate.setImageBitmap(bitmap)
        bottomSheetDialogUpdateAvatarUser.dismiss()

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
                            updateUserProfileViewModel.isUpdateAvatarToSever().subscribe { check ->
                                if (check) {
                                    userManagerUtil.setUserUrlAvatar(uri.toString())
                                    if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                                            getVisibilityView(false)
                                }
                            }
                            updateUserProfileViewModel.updateUserAvatarRequest(
                                userManagerUtil.getUserId(),
                                uri.toString()
                            )
                        }
                    })
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        salesManagerViewModel = SalesManagerViewModel(context)
        rxPermissionsSTORAGE = RxPermissions(FragmentActivity())
        userManagerUtil = UserManagerUtil.getInstance(context)
        updateUserProfileViewModel = UpdateUserProfileViewModel(context)
    }

}