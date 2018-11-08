package com.uit.daniel.hotsalesmanager.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserNameRequest(
    @SerializedName("user")
    @Expose
    var user: UserName? = null
)

data class UserName(

    @SerializedName("name")
    @Expose
    var name: String? = null

)