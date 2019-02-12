package com.uit.daniel.hotsalesmanager

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.uit.daniel.hotsalesmanager.view.salesmanager.SalesManagerActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import android.content.Context.MODE_PRIVATE
import android.R.id.edit
import android.app.PendingIntent.getActivity
import android.content.Context
import com.google.firebase.internal.FirebaseAppHelper.getToken
import com.google.firebase.iid.FirebaseInstanceId
import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this)

        createKeyHashFacebook()

        getTokenFirebase()

        initView()
        setTimeSleep()
    }

    private fun getTokenFirebase() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) { instanceIdResult ->
            val newToken = instanceIdResult.token
            Log.e("newToken", newToken)

            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("abc", newToken)
            clipboard.setPrimaryClip(clip)

            this.getPreferences(Context.MODE_PRIVATE).edit().putString("fb", newToken).apply()
        }

        Log.d("newToken", this.getPreferences(Context.MODE_PRIVATE).getString("fb", "empty :("))
    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun createKeyHashFacebook() {
        try {
            val info = packageManager.getPackageInfo(
                application.packageName,
                PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) { }
    }

    @SuppressLint("CheckResult")
    private fun setTimeSleep() {
        val handler = Handler()
        handler.postDelayed({
            startSalesManagerActivity()
        }, 2000)
    }

    private fun startSalesManagerActivity() {
        val intent = Intent(this, SalesManagerActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }
}
