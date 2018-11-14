package com.uit.daniel.hotsalesmanager.view.product.createproduct

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.request.Product
import com.uit.daniel.hotsalesmanager.data.request.ProductRequest
import com.uit.daniel.hotsalesmanager.service.ProductService
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

interface CreateProductViewModelInputs {
    fun createProduct(name: String, price: Int, discount: Int, type: Int, content: String, image: String)

    fun createProductObservable(): Observable<Boolean>
}

interface CreateProductViewModelOutputs {

}

class CreateGroupViewModel(context: Context) : CreateProductViewModelInputs, CreateProductViewModelOutputs {

    private val userManagerUtil = UserManagerUtil.getInstance(context)
    private var productService: ProductService = ProductService.getInstance(context)

    private val createProductPublishSubject = PublishSubject.create<Boolean>()

    override fun createProductObservable(): Observable<Boolean> = createProductPublishSubject

    @SuppressLint("CheckResult")
    override fun createProduct(name: String, price: Int, discount: Int, type: Int, content: String, image: String) {
        val product = Product()
        product.name = name
        product.price = price
        product.discount = discount
        product.type = type
        product.content = content
        product.image = image
        product.userId = userManagerUtil.getUserId()
        product.lat = userManagerUtil.getLat()
        product.lng = userManagerUtil.getLng()
        product.isWebsite = false
        product.phonenumber = userManagerUtil.getUserPhoneNumberVerifired()
        val productRequest = ProductRequest(product)
        productService.createProductRequest(productRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ productsResponse ->
                createProductPublishSubject.onNext(true)
            },
                { error ->
                    Log.e("ErrorProduct", error.message.toString())
                    createProductPublishSubject.onNext(true)
                })
    }

}