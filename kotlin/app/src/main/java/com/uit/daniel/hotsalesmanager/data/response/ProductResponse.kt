package com.uit.daniel.hotsalesmanager.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class ProductResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,

    @SerializedName("result")
    @Expose
    var result: List<ProductResult>? = null

)

data class ProductResult(

    @SerializedName("_id")
    @Expose
    var id: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("price")
    @Expose
    var price: Int? = null,

    @SerializedName("discount")
    @Expose
    var discount: Int? = null,

    @SerializedName("type")
    @Expose
    var type: Int? = null,

    @SerializedName("content")
    @Expose
    var content: String? = null,

    @SerializedName("image")
    @Expose
    var image: String? = null,

    @SerializedName("owner")
    @Expose
    var owner: Owner? = null,

    @SerializedName("lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("lng")
    @Expose
    var lng: Double? = null,

    @SerializedName("isWebsite")
    @Expose
    var isWebsite: Boolean? = null,

    @SerializedName("date_created")
    @Expose
    var dateCreated: Date? = null,

    @SerializedName("__v")
    @Expose
    var v: Int? = null

)

data class Owner(

    @SerializedName("_id")
    @Expose
    var id: String? = null,

    @SerializedName("avatar")
    @Expose
    var avatar: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("password")
    @Expose
    var password: String? = null,

    @SerializedName("createAt")
    @Expose
    var createAt: String? = null,

    @SerializedName("__v")
    @Expose
    var v: Int? = null

)