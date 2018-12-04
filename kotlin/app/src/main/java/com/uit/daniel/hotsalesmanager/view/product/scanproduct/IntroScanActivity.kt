package com.uit.daniel.hotsalesmanager.view.product.scanproduct

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uit.daniel.hotsalesmanager.R
import kotlinx.android.synthetic.main.activity_intro_scan.*
import kotlinx.android.synthetic.main.dialog_permission_camera.*

class IntroScanActivity : AppCompatActivity() {

    private val rxPermissionsCAMERA = RxPermissions(this)
    private lateinit var dialogPermissionLocation: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_scan)

        initView()
        addControls()
        setPermission()
        addeEvents()
    }

    private fun addControls() {
        dialogPermissionLocation = Dialog(this)
        dialogPermissionLocation.setContentView(R.layout.dialog_permission_camera)
        dialogPermissionLocation.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    @SuppressLint("CheckResult")
    private fun setPermission() {
        var permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permissionStorage == PackageManager.PERMISSION_GRANTED) {

        } else {
            dialogPermissionLocation.show()
            dialogPermissionLocation.tvAccept.setOnClickListener {
                dialogPermissionLocation.dismiss()
                rxPermissionsCAMERA
                    .request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { granted ->
                        if (granted) {
                        } else {
                        }
                    }
            }
            dialogPermissionLocation.tvCancel.setOnClickListener {
                dialogPermissionLocation.dismiss()
            }
        }
    }

    private fun addeEvents() {
        btScan.setOnClickListener {
            val intent = Intent(this, ScanProductActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onResume() {
        super.onResume()
        setPermission()
    }
}
