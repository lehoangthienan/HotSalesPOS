package com.uit.daniel.hotsalesmanager.view.signin.updateuserphonenumber

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import foundation.dwarves.findfriends.data.firebase.phonenumberfirebase.PhoneNumberFirebase
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface UpdatePhoneNumberViewModelInputs {

    fun setCodeAtPosition1(digit: String)
    fun setCodeAtPosition2(digit: String)
    fun setCodeAtPosition3(digit: String)
    fun setCodeAtPosition4(digit: String)
    fun setCodeAtPosition5(digit: String)
    fun setCodeAtPosition6(digit: String)

    fun sendConfirmationCode(phoneNumber: String, activity: Activity, context: Context)

}

interface UpdatePhoneNumberViewModelOutputs {

    fun setConfirmationCodeFocusPosition(): Observable<Int>

    fun onValidationResult(): Observable<Boolean>

}

class UpdatePhoneNumberViewModel : UpdatePhoneNumberViewModelInputs, UpdatePhoneNumberViewModelOutputs {

    private var digit1 = ""
    private var digit2 = ""
    private var digit3 = ""
    private var digit4 = ""
    private var digit5 = ""
    private var digit6 = ""


    private var verifyPhoneNumberFireBase = PhoneNumberFirebase()

    private val setFocusPositionPublicSubject = PublishSubject.create<Int>()

    private val onValidationResultPublishSubject = PublishSubject.create<Boolean>()

    override fun setConfirmationCodeFocusPosition(): Observable<Int> = setFocusPositionPublicSubject

    override fun onValidationResult(): Observable<Boolean> = onValidationResultPublishSubject

    override fun setCodeAtPosition1(digit: String) {
        setFocusPositionPublicSubject.onNext(2)

        digit1 = digit
    }

    override fun setCodeAtPosition2(digit: String) {
        setFocusPositionPublicSubject.onNext(3)

        digit2 = digit
    }

    override fun setCodeAtPosition3(digit: String) {
        setFocusPositionPublicSubject.onNext(4)

        digit3 = digit
    }

    override fun setCodeAtPosition4(digit: String) {

        setFocusPositionPublicSubject.onNext(5)

        digit4 = digit
    }

    override fun setCodeAtPosition5(digit: String) {

        setFocusPositionPublicSubject.onNext(6)

        digit5 = digit
    }

    @SuppressLint("CheckResult")
    override fun setCodeAtPosition6(digit: String) {
        digit6 = digit

        if (digit6.isNotEmpty()) {
            verifyPhoneNumberFireBase.checkCodeServerFirebase(getCodeFromDigits())
            verifyPhoneNumberFireBase.checkVerifiedCodePhoneNumber().subscribe { result ->
                onValidationResultPublishSubject.onNext(result)
            }
        }
    }

    override fun sendConfirmationCode(phoneNumber: String, activity: Activity, context: Context) {
        verifyPhoneNumberFireBase.sendCodeToPhoneNumber(phoneNumber, activity, context)
    }

    private fun getCodeFromDigits(): String {
        return digit1 + digit2 + digit3 + digit4 + digit5 + digit6
    }
}