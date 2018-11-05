package com.uit.daniel.hotsalesmanager.view.signin.updateuserprofile

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.PhoneNumberUtil
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.view.custom.countrycode.Country
import com.uit.daniel.hotsalesmanager.view.custom.countrycode.CustomAdapter
import com.uit.daniel.hotsalesmanager.view.custom.countrycode.listCodeCountry
import com.uit.daniel.hotsalesmanager.view.signin.updateuserphonenumber.UpdatePhoneNumberActivity
import kotlinx.android.synthetic.main.dialog_not_input_phone_number.*
import kotlinx.android.synthetic.main.fragment_update_user_profile.*

class UpdateUserProfileFragment : android.app.Fragment() {

    private lateinit var userManagerUtil: UserManagerUtil
    private var phoneNumber: String = ""
    private var phoneNumberVerified: String = ""
    private var codeCountry: String = ""
    private var userName: String = ""
    private var urlAvatarUser: String = ""
    private var check: Boolean = false
    private lateinit var dlNotInputPhoneNumber: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update_user_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handler = Handler()
        handler.postDelayed({
            initeDialogNotInputPhoneNumber()
            addItemSpinerCodeCountry()
            setFocusInitView()
            setDefaultValueOfSpinner()
            getCodeCountryPhoneNumber()
            getDataUser()
            initView()
            addEvents()
        }, 1000)
    }

    private fun initeDialogNotInputPhoneNumber() {
        dlNotInputPhoneNumber = Dialog(activity)
        dlNotInputPhoneNumber.setContentView(R.layout.dialog_not_input_phone_number)
        dlNotInputPhoneNumber.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setDefaultValueOfSpinner() {
        spCodeCoutry.setSelection(239)
    }

    private fun addItemSpinerCodeCountry() {
        val countries = listCodeCountry(activity)
        val adapteSpinnerCountryCode =
            CustomAdapter(activity, R.layout.fragment_update_user_profile, countries)
        spCodeCoutry?.adapter = adapteSpinnerCountryCode
    }

    private fun setFocusInitView() {
        if (etName?.text?.toString() != null) {
            if (etName?.text?.toString()!!.isEmpty()) {
                etName?.requestFocus()
            } else {
                etPhoneNumber?.requestFocus()
            }
        }
    }

    private fun initView() {
        try {
            if (!userName.isEmpty()) {
                etName.setText(userName)
            }
            if (!urlAvatarUser.isEmpty()) {
                Glide.with(activity)
                    .asBitmap()
                    .load(urlAvatarUser)
                    .into(imgAvatarUpdate)
            }
        } catch (e: Exception) {

        }
    }

    override fun onResume() {
        super.onResume()
        if (phoneNumberVerified.isEmpty()) {
            etPhoneNumber.setText("")
        }
    }

    private fun addEvents() {
        tvCheckFinish.setOnClickListener {
            if (etPhoneNumber.text.toString().isNullOrBlank()) {
                dlNotInputPhoneNumber.show()
            } else {
                getCodeCountryPhoneNumber()
                startConfirmPhoneNumberActivity()
            }
        }
        dlNotInputPhoneNumber.btUpdate.setOnClickListener {
            dlNotInputPhoneNumber.dismiss()
        }
        dlNotInputPhoneNumber.btSkip.setOnClickListener {
            dlNotInputPhoneNumber.dismiss()
            activity.finish()
        }
        tvBack.setOnClickListener {
            activity.finish()
        }
    }

    private fun startConfirmPhoneNumberActivity() {
        val intent = Intent(activity, UpdatePhoneNumberActivity::class.java)
        startActivity(intent)
    }

    private fun getCodeCountryPhoneNumber() {
        spCodeCoutry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                codeCountry = "+84"
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val country: Country = p0?.getItemAtPosition(p2) as Country
                codeCountry = country.code
            }
        }
        if (!etPhoneNumber?.text.toString().isEmpty()) {
            setPhoneNumberInUtils()
        }
    }

    private fun setPhoneNumberInUtils() {
        var phoneNumberMain: String = etPhoneNumber?.text.toString()
        phoneNumberMain = PhoneNumberUtil().replacePhoneNumber(phoneNumberMain)
        phoneNumber = codeCountry + phoneNumberMain
        userManagerUtil.setUserPhoneNumber(phoneNumber)
    }

    private fun getDataUser() {
        userName = userManagerUtil.getUserName()
        urlAvatarUser = userManagerUtil.getUrlkUserAvatar()
        check = userManagerUtil.getCheck()
        phoneNumber = userManagerUtil.getUserPhoneNumber()
        phoneNumberVerified = userManagerUtil.getUserPhoneNumberVerifired()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
    }

}