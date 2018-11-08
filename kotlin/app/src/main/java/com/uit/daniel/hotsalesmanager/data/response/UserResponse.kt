package com.uit.daniel.hotsalesmanager.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class UserResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,

    @SerializedName("result")
    @Expose
    var result: UserResult? = null
)

data class UserResult(

    @SerializedName("createAt")
    @Expose
    var createAt: String? = null,

    @SerializedName("_id")
    @Expose
    var id: String? = null,

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
    var avatar: String? = null,

    @SerializedName("date_created")
    @Expose
    var dateCreated: String? = null,

    @SerializedName("__v")
    @Expose
    var v: Int? = null

)