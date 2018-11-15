package com.uit.daniel.hotsalesmanager.view.product.productdetail

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.service.ProductService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

interface ProductDetailViewModelInputs {

}

interface ProductDetailViewModelOutputs {

    fun product(id: String)

    fun productObservable(): Observable<ProductResponse>
}

class ProductDetailViewModel(context: Context) : ProductDetailViewModelInputs, ProductDetailViewModelOutputs {

    private val productPublishSubject = PublishSubject.create<ProductResponse>()

    override fun productObservable(): Observable<ProductResponse> = productPublishSubject

    private var productService: ProductService = ProductService.getInstance(context)

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
}