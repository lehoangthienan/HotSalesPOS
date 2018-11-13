package com.uit.daniel.hotsalesmanager.service

import android.content.Context
import com.uit.daniel.hotsalesmanager.data.request.ProductRequest
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.utils.ArgumentSingletonHolder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.*

interface ProductApi {
    @GET(ApiEndpoint.GET_ALL_PRODUCT)
    fun pruducts(): Single<ProductResponse>

    @GET(ApiEndpoint.GET_PRODUCT)
    fun userPruducts(@Path("userId") userId: String): Single<ProductResponse>

    @PUT(ApiEndpoint.UPDATE_PRODUCT)
    fun updateProduct(@Path("productId") productId: String, productRequest: ProductRequest): Single<ProductResponse>

    @DELETE(ApiEndpoint.DELETE_PRODUCT)
    fun deleteProduct(@Path("productId") productId: String): Single<ProductResponse>

    @POST(ApiEndpoint.CREATE_PRODUCT)
    fun createProduct(productRequest: ProductRequest): Single<ProductResponse>
}

class ProductService private constructor(context: Context) {
    companion object : ArgumentSingletonHolder<ProductService, Context>(::ProductService)

    private val apiClient: Retrofit =
        ApiService.Factory.getRetrofitBuilder(context).baseUrl(ApiEndpoint.BASE_URL).build()

    private val api: ProductApi = apiClient.create(ProductApi::class.java)

    fun productsRequest() = api.pruducts()

    fun userPruductsRequest(userId: String) = api.userPruducts(userId)

    fun updateProductRequest(productId: String, productRequest: ProductRequest) =
        api.updateProduct(productId, productRequest)

    fun deleteProductRequest(productId: String) = api.deleteProduct(productId)

    fun createProductRequest(productRequest: ProductRequest) =
        api.createProduct(productRequest)

}