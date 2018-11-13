package com.uit.daniel.hotsalesmanager.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductRequest {
    @SerializedName("product")
    @Expose
    var product: Product? = null
}

data class Product(

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("price")
    @Expose
    var price: String? = null,

    @SerializedName("discount")
    @Expose
    var discount: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

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
    var lat: String? = null,

    @SerializedName("lng")
    @Expose
    var lng: String? = null,

    @SerializedName("isWebsite")
    @Expose
    var isWebsite: String? = null,

    @SerializedName("phonenumber")
    @Expose
    var phonenumber: String? = null

)