package com.uit.daniel.hotsalesmanager.view.order.orderuser

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.response.OrderResponse
import com.uit.daniel.hotsalesmanager.service.OrderService
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

interface OrderUserViewModelInputs {

    fun deleteOrder(orderId: String)

    fun deleteOrderObservable(): Observable<Boolean>

}

interface OrderUserViewModelOutputs {

    fun orders(productId: String)

    fun ordersObservable(): Observable<OrderResponse>

}

class OrderUserViewModel(context: Context) : OrderUserViewModelInputs, OrderUserViewModelOutputs {

    private val ordersPublishSubject = PublishSubject.create<OrderResponse>()

    override fun ordersObservable(): Observable<OrderResponse> = ordersPublishSubject

    private val deleteOrderPublishSubject = PublishSubject.create<Boolean>()

    override fun deleteOrderObservable(): Observable<Boolean> = deleteOrderPublishSubject

    private var orderService: OrderService = OrderService.getInstance(context)
    private var userManagerUtil = UserManagerUtil.getInstance(context)

    @SuppressLint("CheckResult")
    override fun orders(productId: String) {
        orderService.orderByProductRequest(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ ordersResponse ->
                ordersPublishSubject.onNext(ordersResponse)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                })
    }

    @SuppressLint("CheckResult")
    override fun deleteOrder(orderId: String) {
        orderService.deleteOrderRequest(orderId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ productsResponse ->
                deleteOrderPublishSubject.onNext(true)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                    deleteOrderPublishSubject.onNext(false)
                })
    }
}