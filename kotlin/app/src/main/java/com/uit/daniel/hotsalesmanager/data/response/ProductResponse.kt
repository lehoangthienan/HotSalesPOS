package com.uit.daniel.hotsalesmanager.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ProductResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,

    @SerializedName("result")
    @Expose
    var result: ProductResult? = null

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
    var discount: String? = null,

    @SerializedName("type")
    @Expose
    var type: Int? = null,

    @SerializedName("content")
    @Expose
    var content: String? = null,

    @SerializedName("image")
    @Expose
    var image: String? = null,

    @SerializedName("user_id")
    @Expose
    var userId: String? = null,

    @SerializedName("lat")
    @Expose
    var lat: Float? = null,

    @SerializedName("lng")
    @Expose
    var lng: Float? = null,

    @SerializedName("isWebsite")
    @Expose
    var isWebsite: Boolean? = null,

    @SerializedName("owner")
    @Expose
    var owner: String? = null,

    @SerializedName("date_created")
    @Expose
    var dateCreated: String? = null,

    @SerializedName("__v")
    @Expose
    var v: Int? = null

)