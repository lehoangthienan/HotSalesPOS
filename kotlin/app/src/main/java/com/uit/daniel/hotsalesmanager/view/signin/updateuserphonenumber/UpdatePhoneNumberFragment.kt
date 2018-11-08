package com.uit.daniel.hotsalesmanager.view.signin.updateuserphonenumber

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.view.salesmanager.SalesManagerActivity
import kotlinx.android.synthetic.main.fragment_update_phone_number.*

class UpdatePhoneNumberFragment : android.app.Fragment() {

    private lateinit var updatePhoneNumberViewModel: UpdatePhoneNumberViewModel
    private var phoneNumber: String = ""
    private lateinit var userManagerUtil: UserManagerUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update_phone_number, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPhoneNumber()
        initView()
        sendCodeToPhoneNumber()
        addEvents()
    }

    private fun getPhoneNumber() {
        phoneNumber = userManagerUtil.getUserPhoneNumber()
    }

    private fun initView() {
        edtCheckNumber1.requestFocus()
    }

    private fun sendCodeToPhoneNumber() {
        updatePhoneNumberViewModel.sendConfirmationCode(phoneNumber, activity, activity)
    }


    @SuppressLint("CheckResult")
    private fun addEvents() {
        tvResendConfirmPhone.setOnClickListener {
            resendConfirmationCodeAndUpdateView()
        }
        tvBack?.setOnClickListener {
            activity.finish()
        }
        RxTextView.textChanges(edtCheckNumber1).subscribe { text ->
            updatePhoneNumberViewModel.setCodeAtPosition1(text.toString())
        }
        RxTextView.textChanges(edtCheckNumber2).subscribe { text ->
            updatePhoneNumberViewModel.setCodeAtPosition2(text.toString())
        }
        RxTextView.textChanges(edtCheckNumber3).subscribe { text ->
            updatePhoneNumberViewModel.setCodeAtPosition3(text.toString())
        }
        RxTextView.textChanges(edtCheckNumber4).subscribe { text ->
            updatePhoneNumberViewModel.setCodeAtPosition4(text.toString())
        }
        RxTextView.textChanges(edtCheckNumber5).subscribe { text ->
            updatePhoneNumberViewModel.setCodeAtPosition5(text.toString())
        }
        RxTextView.textChanges(edtCheckNumber6).subscribe { text ->
            updatePhoneNumberViewModel.setCodeAtPosition6(text.toString())
        }

        setDeleteCodeEditText2()
        setDeleteCodeEditText3()
        setDeleteCodeEditText4()
        setDeleteCodeEditText5()
        setDeleteCodeEditText6()

        updatePhoneNumberViewModel.setConfirmationCodeFocusPosition().subscribe { position ->
            when (position) {
                1 -> edtCheckNumber1.requestFocus()
                2 -> edtCheckNumber2.requestFocus()
                3 -> edtCheckNumber3.requestFocus()
                4 -> edtCheckNumber4.requestFocus()
                5 -> edtCheckNumber5.requestFocus()
                6 -> edtCheckNumber6.requestFocus()
            }
        }

        updatePhoneNumberViewModel.onValidationResult().subscribe { result ->
            Log.d("Anleresult", result.toString())
            if (result) {
                userManagerUtil.setUserPhoneNumberVerifired(phoneNumber)
                updatePhoneNumberViewModel.updatePhoneNumberToSever(userManagerUtil.getUserId(), phoneNumber)
                startSalesManagerActivity()
            }
        }
    }

    private fun setDeleteCodeEditText6() {
        edtCheckNumber6.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                edtCheckNumber6.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        setFormatEditTextForCode()
                    }
                    false
                }
            }
        }
    }

    private fun setDeleteCodeEditText5() {
        edtCheckNumber5.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                edtCheckNumber5.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        setFormatEditTextForCode()
                    }
                    false
                }
            }
        }
    }

    private fun setDeleteCodeEditText4() {
        edtCheckNumber4.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                edtCheckNumber4.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        setFormatEditTextForCode()
                    }
                    false
                }
            }
        }
    }

    private fun setDeleteCodeEditText3() {
        edtCheckNumber3.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                edtCheckNumber3.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        setFormatEditTextForCode()
                    }
                    false
                }
            }
        }
    }

    private fun setDeleteCodeEditText2() {
        edtCheckNumber2.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                edtCheckNumber2.setOnKeyListener { _, keyCode, _ ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        setFormatEditTextForCode()
                    }
                    false
                }
            }
        }
    }

    private fun setFormatEditTextForCode() {
        edtCheckNumber1.setText("")
        edtCheckNumber2.setText("")
        edtCheckNumber3.setText("")
        edtCheckNumber4.setText("")
        edtCheckNumber5.setText("")
        edtCheckNumber6.setText("")
        edtCheckNumber1.requestFocus()
    }

    private fun startSalesManagerActivity() {
        val intent = Intent(activity, SalesManagerActivity::class.java)
        startActivity(intent)
    }

    private fun resendConfirmationCodeAndUpdateView() {
        updatePhoneNumberViewModel.sendConfirmationCode(phoneNumber, activity, activity)
        tvContentSupportConfirmPhone?.setText(R.string.content_resend_confirm_phonenumber)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
        updatePhoneNumberViewModel = UpdatePhoneNumberViewModel(context)
    }
}