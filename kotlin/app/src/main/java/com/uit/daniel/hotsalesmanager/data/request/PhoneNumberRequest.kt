package com.uit.daniel.hotsalesmanager.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PhoneNumberRequest(
    @SerializedName("user")
    @Expose
    var user: PhoneNumber? = null
)

data class PhoneNumber(

    @SerializedName("phone_number")
    @Expose
    var phonenumber: String? = null

)