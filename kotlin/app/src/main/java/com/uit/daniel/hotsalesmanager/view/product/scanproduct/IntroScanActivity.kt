package com.uit.daniel.hotsalesmanager.view.product.scanproduct

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uit.daniel.hotsalesmanager.R
import kotlinx.android.synthetic.main.activity_intro_scan.*

class IntroScanActivity : AppCompatActivity() {

    private val rxPermissionsCAMERA = RxPermissions(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_scan)

        initView()
        setPermission()
        addeEvents()
    }

    @SuppressLint("CheckResult")
    private fun setPermission() {
        var permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permissionStorage == PackageManager.PERMISSION_GRANTED) {

        } else {
            rxPermissionsCAMERA
                .request(Manifest.permission.CAMERA, Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) {
                        //Not permission
                    } else {
                        permissionStorage =
                                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    }
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
}
