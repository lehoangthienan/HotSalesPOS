package com.uit.daniel.hotsalesmanager.utils

import android.content.Context
import android.content.SharedPreferences

class UserManagerUtil constructor(var context: Context) {

    companion object : ArgumentSingletonHolder<UserManagerUtil, Context>(::UserManagerUtil)

    private var sharedPrefsExtraUserInformation: SharedPreferences =
        context.getSharedPreferences(Constant.EXTRA_USER_INFORMATION, Context.MODE_PRIVATE)

    fun setExtraUserInformation(
        userId: String,
        name: String,
        urlUserAvatar: String,
        facebookID: String,
        check: Boolean
    ) {
        val editor = sharedPrefsExtraUserInformation.edit()
        editor.putString(Constant.USER_ID, userId)
        editor.putString(Constant.USER_NAME, name)
        editor.putBoolean(Constant.CHECK_SIGNINFB, check)
        editor.putString(Constant.URL_AVATAR_USER, urlUserAvatar)
        editor.putString(Constant.FACEBOOK_ID, facebookID)
        editor.apply()
    }

    fun setUserName(name: String) {
        val editor = sharedPrefsExtraUserInformation.edit()
        editor.putString(Constant.USER_NAME, name)
        editor.apply()
    }

    fun setUserUrlAvatar(urlUserAvatar: String) {
        val editor = sharedPrefsExtraUserInformation.edit()
        editor.putString(Constant.URL_AVATAR_USER, urlUserAvatar)
        editor.apply()
    }

    fun setUserPhoneNumberVerifired(phoneNumber: String) {
        val editor = sharedPrefsExtraUserInformation.edit()
        editor.putString(Constant.PHONE_NUMBER_VERIFIED, phoneNumber)
        editor.apply()
    }

    fun setUserPhoneNumber(phoneNumber: String) {
        val editor = sharedPrefsExtraUserInformation.edit()
        editor.putString(Constant.PHONE_NUMBER, phoneNumber)
        editor.apply()
    }

    fun setLat(lat: Double) {
        val editor = sharedPrefsExtraUserInformation.edit()
        editor.putString(Constant.LAT, lat.toString())
        editor.apply()
    }

    fun setLng(lng: Double) {
        val editor = sharedPrefsExtraUserInformation.edit()
        editor.putString(Constant.LNG, lng.toString())
        editor.apply()
    }

    fun getLat(): Double {
        return sharedPrefsExtraUserInformation.getString(Constant.LAT, "0.0").toDouble()
    }

    fun getLng(): Double {
        return sharedPrefsExtraUserInformation.getString(Constant.LNG, "0.0").toDouble()
    }

    fun getCheck(): Boolean {
        return sharedPrefsExtraUserInformation.getBoolean(Constant.CHECK_SIGNINFB, true)
    }

    fun getFacebookID(): String {
        return sharedPrefsExtraUserInformation.getString(Constant.FACEBOOK_ID, "")
    }

    fun getUserName(): String {
        return sharedPrefsExtraUserInformation.getString(Constant.USER_NAME, Constant.NAME_USER_DEFAULT)
    }

    fun getUrlkUserAvatar(): String {
        return sharedPrefsExtraUserInformation.getString(Constant.URL_AVATAR_USER, Constant.URL_AVATAR_DEFAULT)
    }

    fun getUserPhoneNumber(): String {
        return sharedPrefsExtraUserInformation.getString(Constant.PHONE_NUMBER, "")
    }

    fun getUserPhoneNumberVerifired(): String {
        return sharedPrefsExtraUserInformation.getString(Constant.PHONE_NUMBER_VERIFIED, "")
    }

    fun getUserId(): String {
        return sharedPrefsExtraUserInformation.getString(Constant.USER_ID, "")
    }

    fun setAddressLocation(name: String) {
        val editor = sharedPrefsExtraUserInformation.edit()
        editor.putString("AddressLocation", name)
        editor.apply()
    }

    fun getAddressLocation(): String {
        return sharedPrefsExtraUserInformation.getString("AddressLocation", "")
    }
}