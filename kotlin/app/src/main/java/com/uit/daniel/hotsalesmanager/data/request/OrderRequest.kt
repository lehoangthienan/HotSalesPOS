package com.uit.daniel.hotsalesmanager.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderRequest(

    @SerializedName("order")
    @Expose
    var order: Order? = null

)


data class Order(

    @SerializedName("user_id")
    @Expose
    var userId: String? = null,

    @SerializedName("user_name")
    @Expose
    var userName: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null,

    @SerializedName("product_name")
    @Expose
    var productName: String? = null,

    @SerializedName("price")
    @Expose
    var price: String? = null,

    @SerializedName("discount")
    @Expose
    var discount: String? = null,

    @SerializedName("image")
    @Expose
    var image: String? = null,

    @SerializedName("owner")
    @Expose
    var owner: String? = null

)