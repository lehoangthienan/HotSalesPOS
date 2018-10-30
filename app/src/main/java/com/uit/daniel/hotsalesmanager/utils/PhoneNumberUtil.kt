package com.uit.daniel.hotsalesmanager.utils

import android.util.Log

@Suppress("NAME_SHADOWING")
class PhoneNumberUtil {

    fun replacePhoneNumber(phoneNumber: String): String {
        var phoneNumberMain = phoneNumber
        if (phoneNumberMain.substring(0, 1) == "0") {
            phoneNumberMain = phoneNumberMain.substring(1)
            Log.d("AnLePhoneMain", phoneNumberMain)
        }
        return phoneNumberMain
    }

    fun replacePhoneNumberToBackend(phoneNumber: String): String {
        var phoneNumberLocal = phoneNumber
        phoneNumberLocal = phoneNumberLocal.replace("(", "")
        phoneNumberLocal = phoneNumberLocal.replace(")", "")
        return phoneNumberLocal
    }

    fun replacePhoneNumberAfterVerified(phoneNumber: String): String {
        var phoneNumber = phoneNumber
        val index = phoneNumber.indexOf(")")
        phoneNumber = phoneNumber.substring(index + 1)
        return phoneNumber
    }
}