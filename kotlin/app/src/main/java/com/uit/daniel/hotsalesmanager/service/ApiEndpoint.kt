package com.uit.daniel.hotsalesmanager.service

object ApiEndpoint {

    const val BASE_URL = "https://hotsalespos.herokuapp.com/"

    const val CREATE_USER = "api/users"
    const val DELETE_USER = "api/users/{userId}"
    const val UPDATE_USER = "api/users/{userId}"

    const val CREATE_PRODUCT = "api/products"
    const val DELETE_PRODUCT = "api/products/{productId}"
    const val UPDATE_PRODUCT = "api/products/{productId}"
    const val GET_ALL_PRODUCT = "api/products"
    const val GET_PRODUCT = "api/products/user/{userId}"
    const val GET_PRODUCT_ID = "api/products/{productId}"

    const val CREATE_ORDER = "api/orders"
    const val DELETE_ORDER = "api/orders/{orderId}"
    const val UPDATE_ORDER = "api/orders/{orderId}"
    const val GET_ALL_ORDER = "api/orders"
    const val GET_ORDER = "api/orders/user/{userId}"
    const val GET_ORDER_ID = "api/orders/{orderId}"

}