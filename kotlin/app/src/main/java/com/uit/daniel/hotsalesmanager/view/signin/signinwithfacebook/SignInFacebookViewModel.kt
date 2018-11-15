package com.uit.daniel.hotsalesmanager.view.signin.signinwithfacebook

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.request.User
import com.uit.daniel.hotsalesmanager.data.request.UserRequest
import com.uit.daniel.hotsalesmanager.service.UserService
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.json.JSONObject
import java.util.*

interface SignInFacebookViewModelInputs {

    fun loginFacebook(context: Activity, callbackManager: CallbackManager)
    fun isLoggedInFacebook(context: Activity)
    fun isLogInFinish(): Observable<Boolean>
    fun isLoggedFacebook(): Observable<Boolean>

}

interface SignInFacebookViewModelOutputs {

}


class SignInFacebookViewModel(context: Context) : SignInFacebookViewModelInputs, SignInFacebookViewModelOutputs {

    private var userManagerUtil: UserManagerUtil = UserManagerUtil.getInstance(context)
    private var userService: UserService = UserService.getInstance(context)

    private val checkLogInFinishPublishSubject = PublishSubject.create<Boolean>()
    override fun isLogInFinish(): Observable<Boolean> = checkLogInFinishPublishSubject

    private val checkLoggedFacebookhPublishSubject = PublishSubject.create<Boolean>()
    override fun isLoggedFacebook(): Observable<Boolean> = checkLoggedFacebookhPublishSubject

    @SuppressLint("CheckResult")
    override fun isLoggedInFacebook(context: Activity) {
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            val request: GraphRequest = GraphRequest.newMeRequest(
                accessToken
            ) { `object`, _ ->
                Log.d("Anle", `object`.toString())
                if (!getUrlProfilePicture(`object`, context).isEmpty()) {
                    if (!getNameUser(`object`, context).isNullOrEmpty()) {
                        val user = User(
                            getNameUser(`object`, context).toString(),
                            `object`?.getString("email").toString(),
                            "anle",
                            getUrlProfilePicture(`object`, context)
                        )
                        val userRequest = UserRequest(user)

                        userService.signInRequest(userRequest)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ userResponse ->
                                userManagerUtil.setExtraUserInformation(
                                    userResponse.result!!.id!!,
                                    userResponse.result!!.name!!,
                                    userResponse.result!!.avatar!!,
                                    `object`?.getString("id").toString(),
                                    true
                                )
                                userManagerUtil.setUserName(userResponse.result!!.name!!)
                                userManagerUtil.setUserUrlAvatar(userResponse.result!!.avatar!!)
                                checkLoggedFacebookhPublishSubject.onNext(true)
                            },
                                { error ->
                                    Log.e("ERROrsign", error.message.toString())
                                })
                    }
                }
            }
            val parameters = Bundle()
            parameters.putString("fields", "id,name,email,location,birthday")
            request.parameters = parameters
            request.executeAsync()
        }
    }

    override fun loginFacebook(context: Activity, callbackManager: CallbackManager) {
        LoginManager.getInstance().logOut()
        LoginManager.getInstance().logInWithReadPermissions(
            context,
            Arrays.asList("public_profile", "user_friends", "email", "user_birthday")
        )

        FacebookSdk.sdkInitialize(context.applicationContext)
        LoginManager.getInstance().logOut()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                @SuppressLint("HardwareIds", "CheckResult")
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("Anle", "onSuccess")
                    val request: GraphRequest = GraphRequest.newMeRequest(
                        loginResult.accessToken
                    ) { `object`, _ ->
                        Log.d("Anle", `object`.toString())
                        if (!getUrlProfilePicture(`object`, context).isEmpty()) {
                            if (!getNameUser(`object`, context).isNullOrEmpty()) {

                                val user = User(
                                    getNameUser(`object`, context).toString(),
                                    `object`?.getString("email").toString(),
                                    "anle",
                                    getUrlProfilePicture(`object`, context)
                                )
                                val userRequest = UserRequest(user)

                                userService.signInRequest(userRequest)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ userResponse ->
                                        Log.d("xxx", userResponse.toString())
                                        userManagerUtil.setExtraUserInformation(
                                            userResponse.result!!.id!!,
                                            userResponse.result!!.name!!,
                                            userResponse.result!!.avatar!!,
                                            `object`?.getString("id").toString(),
                                            true
                                        )
                                        userManagerUtil.setUserName(userResponse.result!!.name!!)
                                        userManagerUtil.setUserUrlAvatar(userResponse.result!!.avatar!!)
                                        checkLogInFinishPublishSubject.onNext(true)
                                    },
                                        { error ->
                                            Log.e("ERROrsign", error.message.toString())
                                        })
                            }
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,email,location,birthday")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getNameUser(jsonObject: JSONObject?, context: Activity): String? {
        var name: String? = ""
        try {
            name = jsonObject?.getString("name")
        } catch (e: Exception) {
        }
        return name
    }

    private fun getUrlProfilePicture(jsonObject: JSONObject?, context: Activity): String {
        var profile_picture: String? = R.string.url_image_user.toString()
        try {
            profile_picture = "https://graph.facebook.com/" + jsonObject?.getString("id") +
                    "/picture?width=250&height=250"
        } catch (e: Exception) {
        }
        return profile_picture.toString()
    }

}