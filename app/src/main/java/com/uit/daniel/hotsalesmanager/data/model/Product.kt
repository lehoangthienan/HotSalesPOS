package com.uit.daniel.hotsalesmanager.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("id")
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

    @SerializedName("image")
    @Expose
    var image: String? = null,

    @SerializedName("branch")
    @Expose
    var branch: String? = null,

    @SerializedName("content")
    @Expose
    var content: String? = null
)