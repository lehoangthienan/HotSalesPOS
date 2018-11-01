package com.uit.daniel.hotsalesmanager.view.product.productaddedcart

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.model.Product
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.view.custom.productadded.ProductAddedAdapter
import kotlinx.android.synthetic.main.fragment_product_added_cart.*

class ProductAddedCartFragment : Fragment() {

    private lateinit var userManagerUtil: UserManagerUtil
    private var products = ArrayList<Product>()
    private lateinit var productAddedAdapter: ProductAddedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_added_cart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFakeData()
        setProductsAdapter()
        setProductsView()
        addEvents()
    }

    private fun setProductsView() {
        rvProducts.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = productAddedAdapter
        }
    }

    private fun setProductsAdapter() {
        productAddedAdapter = ProductAddedAdapter(products)
    }

    private fun initFakeData() {
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "TiKi"
            )
        )
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "Lazada"
            )
        )
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "TiKi"
            )
        )
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "Sendo"
            )
        )
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "TiKi"
            )
        )
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "ChoTot"
            )
        )
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "TiKi"
            )
        )
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "AnLe"
            )
        )
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "TiKi"
            )
        )
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            setProductsAdapter()
            setProductsView()
        } catch (e: Exception) {
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userManagerUtil = UserManagerUtil(context)
    }

}