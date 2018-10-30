package com.uit.daniel.hotsalesmanager.view.signin.signinwithfacebook

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.view.signin.updateuserprofile.UpdateUserProfileActivity
import kotlinx.android.synthetic.main.fragment_signin_facebook.*

class SignInFacebookFragment : Fragment() {

    private lateinit var signInFacebookViewModel: SignInFacebookViewModel
    private var callbackManager = CallbackManager.Factory.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin_facebook, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLogInSuccessFacebook()
        checkLoggedIn()
        addEvents()
    }

    @SuppressLint("CheckResult")
    private fun checkLogInSuccessFacebook() {
        signInFacebookViewModel.isLogInFinish().subscribe { check ->
            if (check) startUpdateUserProfileActivity()
        }
    }

    private fun startUpdateUserProfileActivity() {
        val intent = Intent(activity, UpdateUserProfileActivity::class.java)
        startActivity(intent)
    }

    private fun addEvents() {
        btLoginFb.setOnClickListener {
            signInFacebookViewModel.loginFacebook(activity, callbackManager)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signInFacebookViewModel = SignInFacebookViewModel(context)
    }

    @SuppressLint("CheckResult")
    private fun checkLoggedIn() {
        signInFacebookViewModel.isLoggedFacebook().subscribe { check ->
        }
        signInFacebookViewModel.isLoggedInFacebook(activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}