package com.uit.daniel.hotsalesmanager.view.signin.updateuserprofile

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.RelativeLayout
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.CameraUtils
import com.uit.daniel.hotsalesmanager.utils.ImageUtils
import com.uit.daniel.hotsalesmanager.utils.IntentUtils
import foundation.dwarves.findfriends.utils.Constant
import kotlinx.android.synthetic.main.dialog_permissin_read_write_storage.*
import kotlinx.android.synthetic.main.fragment_update_user_profile.*
import java.io.File


class UpdateUserProfileActivity : AppCompatActivity() {

    private val rxPermissionsSTORAGE = RxPermissions(this)
    private lateinit var bottomSheetDialogUpdateAvatarUser: BottomSheetDialog
    private lateinit var dlPermissionStorage: Dialog
    private var urlUserImage: String = ""
    private lateinit var photoFile: File
    private var imageUtils = ImageUtils()
    private val intentUtils = IntentUtils()
    private val cameraUtils = CameraUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_profile)

        initView()
        inItBottomSheetDialog()
        initeDialogPermissionStorage()
        initView()
        addEvents()
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
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
                            //Not permission
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
            val takePictureIntent = intentUtils.intentActionImageCapture()
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startCameraActivity(takePictureIntent)
            }
        }

        bottomSheetDialogUpdateAvatarUser.findViewById<RelativeLayout>(R.id.takePhotoFromLibrary)?.setOnClickListener {
            startActivityForResult(intentUtils.intentActionPick(), Constant.REQUEST_GALLERY)
        }
    }

    private fun startCameraActivity(takePictureIntent: Intent) {
        photoFile = imageUtils.createImageFile(this)
        cameraUtils.putUriImage(takePictureIntent, imageUtils.createPhotoURI(photoFile, this))
        startActivityForResult(takePictureIntent, Constant.REQUEST_CAMERA)
    }

    private fun inItBottomSheetDialog() {
        bottomSheetDialogUpdateAvatarUser = BottomSheetDialog(this)
        bottomSheetDialogUpdateAvatarUser.setContentView(R.layout.bottomsheetdialog_update_avatar_profile)
        bottomSheetDialogUpdateAvatarUser.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun initeDialogPermissionStorage() {
        dlPermissionStorage = Dialog(this)
        dlPermissionStorage.setContentView(R.layout.dialog_permissin_read_write_storage)
        dlPermissionStorage.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Constant.REQUEST_GALLERY -> {
                setImageForGallery(data)
            }
            Constant.REQUEST_CAMERA -> {
                setImageForCamera()
            }
        }
    }

    private fun setImageForCamera() {
        //Get path from picture take with camera
        urlUserImage = photoFile.absolutePath
        val bitmap = BitmapFactory.decodeFile(urlUserImage)
        imgAvatarUpdate.setImageBitmap(bitmap)
        bottomSheetDialogUpdateAvatarUser.dismiss()
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
    }
}