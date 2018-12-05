package com.uit.daniel.hotsalesmanager.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserAvatarRequest(
    @SerializedName("user")
    @Expose
    var user: Avatar? = null
)

data class Avatar(

    @SerializedName("avatar")
    @Expose
    var avatar: String? = null

)