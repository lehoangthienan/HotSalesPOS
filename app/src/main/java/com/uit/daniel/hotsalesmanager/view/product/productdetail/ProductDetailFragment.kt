package com.uit.daniel.hotsalesmanager.view.product.productdetail

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.model.Product
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import kotlinx.android.synthetic.main.fragment_product_detail.*

class ProductDetailFragment : Fragment() {

    private val priceUtils = PriceUtils()
    private var idProduct: String = ""
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

    @SuppressLint("SetTextI18n")
    private fun initView() {
        tvProductName.text = product.name
        try {
            ivProduct?.let {
                Glide.with(activity)
                    .asBitmap()
                    .load(product.image)
                    .into(it)
            }
        } catch (e: Exception) {
        }
        tvPrice.text = product.price?.let { priceUtils.setStringMoney(it) }
        tvPriceDiscount.text = priceUtils.setStringMoney(product.discount?.let {
            product.price?.let { it1 ->
                priceUtils.priceDiscount(
                    it,
                    it1
                )
            }
        }!!)
        tvPercentDiscount.text = product.discount.toString() + "%"
        tvBranchName.text = product.branch
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
        }
        ivOrder.setOnClickListener {
            startOrderActivity()
        }
        btOrder.setOnClickListener {
            startOrderActivity()
        }
    }

    private fun startOrderActivity() {

    }

}