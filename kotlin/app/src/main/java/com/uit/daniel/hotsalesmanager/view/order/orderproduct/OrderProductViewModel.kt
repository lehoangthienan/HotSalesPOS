package com.uit.daniel.hotsalesmanager.view.order.orderproduct

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.request.Order
import com.uit.daniel.hotsalesmanager.data.request.OrderRequest
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.service.OrderService
import com.uit.daniel.hotsalesmanager.service.ProductService
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

interface OrderProductViewModelInputs {

    fun createOrder(productId: String, name: String, address: String, phonenumber: String)

    fun createOrderObservable(): Observable<Boolean>
}

interface OrderProductViewModelOutputs {

    fun product(id: String)

    fun productObservable(): Observable<ProductResponse>

}

class OrderProductViewModel(context: Context) : OrderProductViewModelInputs, OrderProductViewModelOutputs {

    private val productPublishSubject = PublishSubject.create<ProductResponse>()

    override fun productObservable(): Observable<ProductResponse> = productPublishSubject

    private val createOrderPublishSubject = PublishSubject.create<Boolean>()

    override fun createOrderObservable(): Observable<Boolean> = createOrderPublishSubject

    private var productService: ProductService = ProductService.getInstance(context)
    private var orderService: OrderService = OrderService.getInstance(context)
    private val userManagerUtil = UserManagerUtil.getInstance(context)

    @SuppressLint("CheckResult")
    override fun product(id: String) {
        productService.productRequest(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ productsResponse ->
                productPublishSubject.onNext(productsResponse)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                })
    }

    @SuppressLint("CheckResult")
    override fun createOrder(productId: String, name: String, address: String, phonenumber: String) {
        val order = Order(
            userManagerUtil.getUserId(),
            productId,
            name,
            address,
            userManagerUtil.getLat(),
            userManagerUtil.getLng(),
            phonenumber
        )
        val orderRequest = OrderRequest(order)

        orderService.createOrderRequest(orderRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ orderResponse ->
                createOrderPublishSubject.onNext(true)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                    createOrderPublishSubject.onNext(true)
                })
    }

}