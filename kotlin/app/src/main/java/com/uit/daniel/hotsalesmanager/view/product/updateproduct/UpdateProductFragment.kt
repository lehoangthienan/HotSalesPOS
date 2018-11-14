package com.uit.daniel.hotsalesmanager.view.product.updateproduct

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.utils.ToastSnackBar
import kotlinx.android.synthetic.main.fragment_update_product.*

class UpdateProductFragment : Fragment() {

    private lateinit var updateProductViewModel: UpdateProductViewModel
    private lateinit var productResponse: ProductResponse

    private var productId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update_product, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductId()
        getProductDetail()
        addEvents()
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
        }
        tvCheckFinish.setOnClickListener {
            isFullField()
        }
    }

    private fun isFullField() {
        if (etName.text.toString().isNullOrBlank() ||
            etPrice.text.toString().isNullOrBlank() ||
            etDiscount.text.toString().isNullOrBlank() ||
            etContent.text.toString().isNullOrBlank() ||
            etImageLink.text.toString().isNullOrBlank()
        ) ToastSnackBar.showSnackbar("Please enter full information before proceeding.", view, activity)
        else {
            updateProduct()
        }
    }

    @SuppressLint("CheckResult")
    private fun updateProduct() {
        updateProductViewModel.updateProductObservable().subscribe { check ->
            if (check) activity.finish()
            else ToastSnackBar.showSnackbar("Update product fail!", view, activity)
        }
        setProductResponse()
        updateProductViewModel.updateProduct(productId, productResponse)
    }

    private fun setProductResponse() {
        productResponse.result!![0].name = etName.text.toString()
        productResponse.result!![0].price =  etPrice.text.toString().toInt()
        productResponse.result!![0].discount = etDiscount.text.toString().toInt()
        productResponse.result!![0].content = etContent.text.toString()
        productResponse.result!![0].image = etImageLink.text.toString()
    }

    @SuppressLint("CheckResult")
    private fun getProductDetail() {
        updateProductViewModel.productObservable().subscribe { response ->
            productResponse = response
            initView()
        }
        updateProductViewModel.product(productId)
    }

    private fun initView() {
        etName.setText(productResponse.result!![0].name)
        etPrice.setText(productResponse.result!![0].price.toString())
        etDiscount.setText(productResponse.result!![0].discount.toString())
        etContent.setText(productResponse.result!![0].content)
        etImageLink.setText(productResponse.result!![0].image)
    }

    private fun getProductId() {
        productId = activity?.intent?.getStringExtra("ID") ?: ""
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateProductViewModel = UpdateProductViewModel(context)
    }
}