package com.uit.daniel.hotsalesmanager.view.order.updateorder

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.request.Order
import com.uit.daniel.hotsalesmanager.data.request.OrderRequest
import com.uit.daniel.hotsalesmanager.data.response.OrderResponse
import com.uit.daniel.hotsalesmanager.service.OrderService
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

interface UpdateOrderViewModelInputs {
    fun updateOrder(orderid: String, productId: String, name: String, address: String, phoneNumber: String)

    fun updateOrderObservable(): Observable<Boolean>
}

interface UpdateOrderViewModelOutputs {
    fun orders(id: String)

    fun ordersObservable(): Observable<OrderResponse>
}

class UpdateOrderViewModel(context: Context) : UpdateOrderViewModelInputs, UpdateOrderViewModelOutputs {

    private var orderService: OrderService = OrderService.getInstance(context)
    private val userManagerUtil = UserManagerUtil.getInstance(context)

    private val ordersPublishSubject = PublishSubject.create<OrderResponse>()

    override fun ordersObservable(): Observable<OrderResponse> = ordersPublishSubject

    private val updateOrderPublishSubject = PublishSubject.create<Boolean>()

    override fun updateOrderObservable(): Observable<Boolean> = updateOrderPublishSubject

    @SuppressLint("CheckResult")

    override fun orders(id: String) {
        orderService.orderRequest(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ orderResponse ->
                ordersPublishSubject.onNext(orderResponse)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                })
    }

    @SuppressLint("CheckResult")
    override fun updateOrder(orderid: String, productId: String, name: String, address: String, phoneNumber: String) {
        val order = Order(
            userManagerUtil.getUserId(), productId, name, address, userManagerUtil.getLat(),
            userManagerUtil.getLng(), phoneNumber
        )
        val orderRequest = OrderRequest(order)

        orderService.updateOrderRequest(orderid, orderRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ orderResponse ->
                updateOrderPublishSubject.onNext(true)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                    updateOrderPublishSubject.onNext(true)
                })
    }

}