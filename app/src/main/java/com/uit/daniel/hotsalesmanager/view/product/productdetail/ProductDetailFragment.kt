package com.uit.daniel.hotsalesmanager.view.product.productdetail

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.model.Product

class ProductDetailFragment : Fragment() {

    private var product = Product(
        "1",
        "Iphone Xs Max",
        15000000,
        50,
        "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
        "TiKi"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductDetail()
        initView()
        addEvents()
    }

    private fun getProductDetail() {

    }

    private fun initView() {

    }

    private fun addEvents() {

    }

}