package com.uit.daniel.hotsalesmanager.view.salesmanager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.service.ProductService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

interface SalesManagerViewModelInputs {

}

interface SalesManagerViewModelOutputs {

    fun products()

    fun userProducts(userId: String)

    fun productsObservable(): Observable<ProductResponse>

    fun userProductsObservable(): Observable<ProductResponse>
}

class SalesManagerViewModel(context: Context) : SalesManagerViewModelInputs, SalesManagerViewModelOutputs {

    private val productsPublishSubject = PublishSubject.create<ProductResponse>()

    override fun productsObservable(): Observable<ProductResponse> = productsPublishSubject

    private val userProductsPublishSubject = PublishSubject.create<ProductResponse>()

    override fun userProductsObservable(): Observable<ProductResponse> = userProductsPublishSubject

    private var productService: ProductService = ProductService.getInstance(context)

    @SuppressLint("CheckResult")
    override fun products() {
        productService.productsRequest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ productsResponse ->
                productsPublishSubject.onNext(productsResponse)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                })
    }

    @SuppressLint("CheckResult")
    override fun userProducts(userId: String) {
        productService.userProductsRequest(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ productsResponse ->
                userProductsPublishSubject.onNext(productsResponse)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                })
    }
}