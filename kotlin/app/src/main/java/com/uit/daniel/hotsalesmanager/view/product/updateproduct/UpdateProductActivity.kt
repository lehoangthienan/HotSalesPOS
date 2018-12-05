package com.uit.daniel.hotsalesmanager.view.product.updateproduct

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uit.daniel.hotsalesmanager.R
import kotlinx.android.synthetic.main.dialog_permissin_read_write_storage.*
import kotlinx.android.synthetic.main.dialog_permission_camera.*

class UpdateProductActivity : AppCompatActivity() {

    private val rxPermissionsSTORAGE = RxPermissions(this)
    private lateinit var dlPermissionStorage: Dialog
    private val rxPermissionsCAMERA = RxPermissions(this)
    private lateinit var dialogPermissionCamera: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        initView()
        addControls()
        getPermission()
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }

    private fun addControls() {
        dlPermissionStorage = Dialog(this)
        dlPermissionStorage.setContentView(R.layout.dialog_permissin_read_write_storage)
        dlPermissionStorage.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogPermissionCamera = Dialog(this)
        dialogPermissionCamera.setContentView(R.layout.dialog_permission_camera)
        dialogPermissionCamera.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun getPermissionCamera() {
        val permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permissionCamera == PackageManager.PERMISSION_GRANTED) {
        } else {
            dialogPermissionCamera.show()
            dialogPermissionCamera.tvAccept.setOnClickListener {
                dialogPermissionCamera.dismiss()
                rxPermissionsCAMERA
                    .request(Manifest.permission.CAMERA)
                    .subscribe { granted ->
                        if (granted) {
                        } else {
                        }
                    }
            }
            dialogPermissionCamera.tvCancel.setOnClickListener {
                dialogPermissionCamera.dismiss()
            }
        }
    }

    private fun getPermission() {
        var permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionStorage == PackageManager.PERMISSION_GRANTED) {
            getPermissionCamera()
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
                        }
                        getPermissionCamera()
                    }
            }
            dlPermissionStorage.tvCancelPermissionStorage.setOnClickListener {
                dlPermissionStorage.dismiss()
                getPermissionCamera()
            }
        }
    }
}
