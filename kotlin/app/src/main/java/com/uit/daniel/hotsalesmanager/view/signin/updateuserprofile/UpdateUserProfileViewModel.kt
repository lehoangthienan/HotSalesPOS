package com.uit.daniel.hotsalesmanager.view.signin.updateuserprofile

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.request.UserName
import com.uit.daniel.hotsalesmanager.data.request.UserNameRequest
import com.uit.daniel.hotsalesmanager.service.UserService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

interface UpdateUserProfileViewModelInputs {

    fun updateUserNameToSever(userId: String, userName: String)

    fun isUpdateUserNameToSever(): Observable<Boolean>

}

interface UpdateUserProfileViewModelOutputs {

}

class UpdateUserProfileViewModel(context: Context) : UpdateUserProfileViewModelInputs,
    UpdateUserProfileViewModelOutputs {

    private var userService: UserService = UserService.getInstance(context)

    private val isUpdateUserNameToSeverPublishSubject = PublishSubject.create<Boolean>()
    override fun isUpdateUserNameToSever(): Observable<Boolean> = isUpdateUserNameToSeverPublishSubject

    @SuppressLint("CheckResult")
    override fun updateUserNameToSever(userId: String, userName: String) {
        val userNameObject = UserName(userName)
        val userNameRequest = UserNameRequest(userNameObject)

        userService.updateNameRequest(userId, userNameRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userResponse ->
                isUpdateUserNameToSeverPublishSubject.onNext(true)
            },
                { error ->
                    isUpdateUserNameToSeverPublishSubject.onNext(false)
                    Log.e("ERROrsign", error.message.toString())
                })
    }
}