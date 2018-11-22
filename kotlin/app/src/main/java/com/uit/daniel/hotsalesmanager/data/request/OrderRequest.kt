package com.uit.daniel.hotsalesmanager.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderRequest(

    @SerializedName("order")
    @Expose
    var order: Order? = null

)


data class Order(

    @SerializedName("owner")
    @Expose
    var userId: String? = null,

    @SerializedName("product")
    @Expose
    var productId: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("address")
    @Expose
    var address: String? = null,

    @SerializedName("ownernameproduct")
    @Expose
    var ownernameproduct: String? = null,

    @SerializedName("lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("lng")
    @Expose
    var lng: Double? = null,

    @SerializedName("phonenumber")
    @Expose
    var phonenumber: String? = null

)