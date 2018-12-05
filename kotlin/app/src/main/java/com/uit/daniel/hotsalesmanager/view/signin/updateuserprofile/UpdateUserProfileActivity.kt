package com.uit.daniel.hotsalesmanager.view.signin.updateuserprofile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
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
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.Constant
import com.uit.daniel.hotsalesmanager.utils.Constant.REQUEST_CAMERA
import com.uit.daniel.hotsalesmanager.utils.IntentUtils
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.utils.getVisibilityView
import kotlinx.android.synthetic.main.dialog_permissin_read_write_storage.*
import kotlinx.android.synthetic.main.dialog_permission_camera.*
import kotlinx.android.synthetic.main.fragment_update_user_profile.*
import java.io.ByteArrayOutputStream
import java.util.*


class UpdateUserProfileActivity : AppCompatActivity() {

    private val rxPermissionsSTORAGE = RxPermissions(this)
    private lateinit var bottomSheetDialogUpdateAvatarUser: BottomSheetDialog
    private lateinit var dlPermissionStorage: Dialog
    private val intentUtils = IntentUtils()
    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.getReferenceFromUrl("gs://hotsalesmanager-fef89.appspot.com")
    private var updateUserProfileViewModel = UpdateUserProfileViewModel(this@UpdateUserProfileActivity)
    private var userManagerUtil = UserManagerUtil.getInstance(this@UpdateUserProfileActivity)
    private val rxPermissionsCAMERA = RxPermissions(this)
    private lateinit var dialogPermissionCamera: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_profile)

        initView()
        addControls()
        addEvents()
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        if (progressBarAddLocation != null) progressBarAddLocation.visibility =
                getVisibilityView(false)
    }

    private fun addEvents() {
        viewEditAvatar.setOnClickListener {
            setPermission()
        }
    }

    private fun setPermission() {
        var permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
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
                        } else {
                            permissionStorage =
                                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
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
            setPermissionCamera()
        }

        bottomSheetDialogUpdateAvatarUser.findViewById<RelativeLayout>(R.id.takePhotoFromLibrary)?.setOnClickListener {
            startActivityForResult(intentUtils.intentActionPick(), Constant.REQUEST_GALLERY)
        }
    }

    private fun setPermissionCamera() {
        val permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permissionStorage == PackageManager.PERMISSION_GRANTED) {
            startCameraIntent()
        } else {
            dialogPermissionCamera.show()
            dialogPermissionCamera.tvAccept.setOnClickListener {
                dialogPermissionCamera.dismiss()
                rxPermissionsCAMERA
                    .request(Manifest.permission.CAMERA)
                    .subscribe { granted ->
                        if (granted) {
                            startCameraIntent()
                        } else {
                        }
                    }
            }
            dialogPermissionCamera.tvCancel.setOnClickListener {
                dialogPermissionCamera.dismiss()
            }
        }
    }

    private fun startCameraIntent() {
        val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startCameraActivity()
        }
    }

    private fun startCameraActivity() {
        val takePictureIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(this.packageManager) != null) {
            this.startActivityForResult(takePictureIntent, REQUEST_CAMERA)
        }
    }


    private fun addControls() {
        bottomSheetDialogUpdateAvatarUser = BottomSheetDialog(this)
        bottomSheetDialogUpdateAvatarUser.setContentView(R.layout.bottomsheetdialog_update_avatar_profile)
        bottomSheetDialogUpdateAvatarUser.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dlPermissionStorage = Dialog(this)
        dlPermissionStorage.setContentView(R.layout.dialog_permissin_read_write_storage)
        dlPermissionStorage.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogPermissionCamera = Dialog(this)
        dialogPermissionCamera.setContentView(R.layout.dialog_permission_camera)
        dialogPermissionCamera.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

        val cursor = contentResolver.query(uri, projection, null, null, null)
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
}
