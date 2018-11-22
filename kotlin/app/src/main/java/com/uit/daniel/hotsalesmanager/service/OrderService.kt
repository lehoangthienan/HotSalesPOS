package com.uit.daniel.hotsalesmanager.service

import android.content.Context
import com.uit.daniel.hotsalesmanager.data.request.OrderRequest
import com.uit.daniel.hotsalesmanager.data.response.OrderResponse
import com.uit.daniel.hotsalesmanager.utils.ArgumentSingletonHolder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.*

interface OrderApi {
    @GET(ApiEndpoint.GET_ORDER)
    fun orders(@Path("userId") userId: String): Single<OrderResponse>

    @GET(ApiEndpoint.GET_ORDER_ID)
    fun order(@Path("orderId") orderId: String): Single<OrderResponse>

    @GET(ApiEndpoint.GET_ORDER_BY_PRODUCT)
    fun orderByProduct(@Path("productId") productId: String): Single<OrderResponse>

    @PUT(ApiEndpoint.UPDATE_ORDER)
    fun updateOrder(@Path("orderId") orderId: String, @Body orderRequest: OrderRequest): Single<OrderResponse>

    @DELETE(ApiEndpoint.DELETE_ORDER)
    fun deleteOrder(@Path("orderId") orderId: String): Single<OrderResponse>

    @POST(ApiEndpoint.CREATE_ORDER)
    fun createOrder(@Body orderRequest: OrderRequest): Single<OrderResponse>
}

class OrderService private constructor(context: Context) {
    companion object : ArgumentSingletonHolder<OrderService, Context>(::OrderService)

    private val apiClient: Retrofit =
        ApiService.Factory.getRetrofitBuilder(context).baseUrl(ApiEndpoint.BASE_URL).build()

    private val api: OrderApi = apiClient.create(OrderApi::class.java)

    fun ordersRequest(userId: String) = api.orders(userId)

    fun orderRequest(orderId: String) = api.order(orderId)

    fun orderByProductRequest(productId: String) = api.orderByProduct(productId)

    fun updateOrderRequest(orderId: String, orderRequest: OrderRequest) =
        api.updateOrder(orderId, orderRequest)

    fun deleteOrderRequest(orderId: String) = api.deleteOrder(orderId)

    fun createOrderRequest(orderRequest: OrderRequest) =
        api.createOrder(orderRequest)

}