package com.uit.daniel.hotsalesmanager.view.salesmanager

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import kotlinx.android.synthetic.main.dialog_permission_location.*

class SalesManagerActivity : AppCompatActivity(), LocationListener {

    private val rxPermissionsLocation = RxPermissions(this)
    private lateinit var dialogPermissionLocation: Dialog
    private lateinit var userManagerUtils: UserManagerUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_manager)

        initView()
        addControls()
        setPermissionLocation()
    }

    private fun addControls() {
        dialogPermissionLocation = Dialog(this)
        dialogPermissionLocation.setContentView(R.layout.dialog_permission_location)
        dialogPermissionLocation.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        userManagerUtils = UserManagerUtil.getInstance(this@SalesManagerActivity)
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        setPermissionLocation()

    }

    private fun setPermissionLocation() {

        val permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
        } else {
            dialogPermissionLocation.show()
            dialogPermissionLocation.tvAccept.setOnClickListener {
                dialogPermissionLocation.dismiss()
                rxPermissionsLocation
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

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            userManagerUtils.setLat(location.latitude)
            userManagerUtils.setLng(location.longitude)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }
}
