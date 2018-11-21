package com.uit.daniel.hotsalesmanager.service

import android.content.Context
import com.uit.daniel.hotsalesmanager.data.request.ProductRequest
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.utils.ArgumentSingletonHolder
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.http.*


interface ProductApi {
    @GET(ApiEndpoint.GET_ALL_PRODUCT)
    fun products(): Single<ProductResponse>

    @GET(ApiEndpoint.GET_PRODUCT_ID)
    fun product(@Path("productId") productId: String): Single<ProductResponse>

    @GET(ApiEndpoint.GET_PRODUCT)
    fun userProducts(@Path("userId") userId: String): Single<ProductResponse>

    @PUT(ApiEndpoint.UPDATE_PRODUCT)
    fun updateProduct(@Path("productId") productId: String, @Body productRequest: ProductRequest): Single<ProductResponse>

    @DELETE(ApiEndpoint.DELETE_PRODUCT)
    fun deleteProduct(@Path("productId") productId: String): Single<ProductResponse>

    @POST(ApiEndpoint.CREATE_PRODUCT)
    fun createProduct(@Body productRequest: ProductRequest): Single<ProductResponse>

    @POST(ApiEndpoint.CREATE_PRODUCT)
    fun createProductWithImage(@Part image: MultipartBody.Part, @Body productRequest: ProductRequest): Single<ProductResponse>

    @PUT(ApiEndpoint.UPDATE_PRODUCT)
    fun updateProductWithImage(@Path("productId") productId: String, @Part image: MultipartBody.Part, @Body productRequest: ProductRequest): Single<ProductResponse>

    @PUT(ApiEndpoint.UPDATE_PRODUCT)
    fun updateProductImage(@Path("productId") productId: String, @Part image: MultipartBody.Part): Single<ProductResponse>
}

class ProductService private constructor(context: Context) {
    companion object : ArgumentSingletonHolder<ProductService, Context>(::ProductService)

    private val apiClient: Retrofit =
        ApiService.Factory.getRetrofitBuilder(context).baseUrl(ApiEndpoint.BASE_URL).build()

    private val api: ProductApi = apiClient.create(ProductApi::class.java)

    fun productsRequest() = api.products()

    fun productRequest(productId: String) = api.product(productId)

    fun userProductsRequest(userId: String) = api.userProducts(userId)

    fun updateProductRequest(productId: String, productRequest: ProductRequest) =
        api.updateProduct(productId, productRequest)

    fun deleteProductRequest(productId: String) = api.deleteProduct(productId)

    fun createProductRequest(productRequest: ProductRequest) =
        api.createProduct(productRequest)

    fun updateProductImageRequest(productId: String, image: MultipartBody.Part) =
        api.updateProductImage(productId, image)

}