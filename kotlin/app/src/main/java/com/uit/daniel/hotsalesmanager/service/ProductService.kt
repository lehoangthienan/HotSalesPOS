package com.uit.daniel.hotsalesmanager.service

import android.content.Context
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.utils.ArgumentSingletonHolder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET

interface ProductApi {
    @GET(ApiEndpoint.GET_ALL_PRODUCT)
    fun pruducts(): Single<ProductResponse>
}

class ProductService private constructor(context: Context) {
    companion object : ArgumentSingletonHolder<ProductService, Context>(::ProductService)

    private val apiClient: Retrofit =
        ApiService.Factory.getRetrofitBuilder(context).baseUrl(ApiEndpoint.BASE_URL).build()

    private val api: ProductApi = apiClient.create(ProductApi::class.java)

    fun productsRequest() = api.pruducts()

}