package com.uit.daniel.hotsalesmanager.view.signin.signinwithfacebook

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.uit.daniel.hotsalesmanager.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SignInFacebookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_facebook)

        createKeyHashFacebook()
        initView()
    }

    private fun initView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = fragmentManager.findFragmentById(R.id.fragment2)
        fragment.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun createKeyHashFacebook() {
        try {
            val info = packageManager.getPackageInfo(
                application.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

    }
}
