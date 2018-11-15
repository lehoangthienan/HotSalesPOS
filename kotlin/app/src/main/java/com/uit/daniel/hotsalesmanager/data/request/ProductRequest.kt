package com.uit.daniel.hotsalesmanager.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ProductRequest(
    @SerializedName("product")
    @Expose
    var product: Product? = null
)

data class Product(

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
    var userId: String? = null,

    @SerializedName("lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("lng")
    @Expose
    var lng: Double? = null,

    @SerializedName("isWebsite")
    @Expose
    var isWebsite: Boolean? = null,

    @SerializedName("phonenumber")
    @Expose
    var phonenumber: String? = null

)