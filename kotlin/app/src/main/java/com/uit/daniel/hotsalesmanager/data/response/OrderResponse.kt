package com.uit.daniel.hotsalesmanager.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("status")
    @Expose
    var status: Int? = null,

    @SerializedName("result")
    @Expose
    var result: List<OrderResult>? = null
)

data class OrderResult(

    @SerializedName("_id")
    @Expose
    var id: String? = null,

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
    var price: Int? = null,

    @SerializedName("discount")
    @Expose
    var discount: String? = null,

    @SerializedName("image")
    @Expose
    var image: String? = null,

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