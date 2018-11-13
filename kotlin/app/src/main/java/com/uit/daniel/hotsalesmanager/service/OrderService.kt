package com.uit.daniel.hotsalesmanager.service

import com.uit.daniel.hotsalesmanager.data.request.OrderRequest
import com.uit.daniel.hotsalesmanager.data.response.OrderResponse
import io.reactivex.Single
import retrofit2.http.*

interface OrderApi{
    @GET(ApiEndpoint.GET_ORDER)
    fun orders(@Path("userId") userId: String): Single<OrderResponse>

    @PUT(ApiEndpoint.UPDATE_ORDER)
    fun updateOrder(@Path("orderId") productId: String, productRequest: OrderRequest): Single<OrderResponse>

    @DELETE(ApiEndpoint.DELETE_ORDER)
    fun deleteOrder(@Path("orderId") productId: String): Single<OrderResponse>

    @POST(ApiEndpoint.CREATE_ORDER)
    fun createOrder(productRequest: OrderRequest): Single<OrderResponse>
}

class OrderService {
}