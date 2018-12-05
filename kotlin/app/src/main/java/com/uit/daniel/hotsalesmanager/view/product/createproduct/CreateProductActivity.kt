package com.uit.daniel.hotsalesmanager.view.product.createproduct

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uit.daniel.hotsalesmanager.R

class CreateProductActivity : AppCompatActivity() {

    private val rxPermissionsSTORAGE = RxPermissions(this)
    private lateinit var dlPermissionStorage: Dialog
    private val rxPermissionsCAMERA = RxPermissions(this)
    private lateinit var dialogPermissionCamera: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        addControls()
        getPermission()
        initView()
    }

    private fun addControls() {
        
    }

    private fun getPermission() {

    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }
}
