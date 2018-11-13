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
    var productId: String? = null

)