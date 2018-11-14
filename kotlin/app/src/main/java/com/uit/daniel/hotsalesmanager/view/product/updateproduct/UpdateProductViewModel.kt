package com.uit.daniel.hotsalesmanager.view.product.updateproduct

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.request.Product
import com.uit.daniel.hotsalesmanager.data.request.ProductRequest
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.service.ProductService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

interface UpdateProductViewModelInputs {
    fun updateProduct(id: String, productResponse: ProductResponse)

    fun updateProductObservable(): Observable<Boolean>
}

interface UpdateProductViewModelOutputs {

    fun product(id: String)

    fun productObservable(): Observable<ProductResponse>

}

class UpdateProductViewModel(context: Context) : UpdateProductViewModelInputs, UpdateProductViewModelOutputs {

    private val productPublishSubject = PublishSubject.create<ProductResponse>()

    override fun productObservable(): Observable<ProductResponse> = productPublishSubject

    private val updateProductPublishSubject = PublishSubject.create<Boolean>()

    override fun updateProductObservable(): Observable<Boolean> = updateProductPublishSubject

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

    @SuppressLint("CheckResult")
    override fun updateProduct(id: String, productResponse: ProductResponse) {
        val product = Product()
        product.name = productResponse.result?.get(0)?.name
        product.price = productResponse.result?.get(0)?.price
        product.discount = productResponse.result?.get(0)?.discount
        product.type = productResponse.result?.get(0)?.type
        product.content = productResponse.result?.get(0)?.content
        product.image = productResponse.result?.get(0)?.image
        product.userId = productResponse.result?.get(0)?.owner?.id
        product.lat = productResponse.result?.get(0)?.lat
        product.lng = productResponse.result?.get(0)?.lng
        product.isWebsite = productResponse.result?.get(0)?.isWebsite
        product.phonenumber = productResponse.result?.get(0)?.owner?.phone_number
        val productRequest = ProductRequest(product)
        productService.updateProductRequest(id, productRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ productsResponse ->
                updateProductPublishSubject.onNext(true)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                    updateProductPublishSubject.onNext(true)
                })
    }
}