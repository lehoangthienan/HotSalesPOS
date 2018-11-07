package com.uit.daniel.hotsalesmanager.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class UserRequest(
    @SerializedName("user")
    @Expose
    var user: User? = null
)

data class User(

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("password")
    @Expose
    var password: String? = null,

    @SerializedName("avatar")
    @Expose
    var avatar: String? = null

)