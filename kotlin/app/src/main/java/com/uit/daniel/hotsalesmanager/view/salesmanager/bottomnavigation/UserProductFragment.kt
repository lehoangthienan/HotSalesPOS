package com.uit.daniel.hotsalesmanager.view.salesmanager.bottomnavigation

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResult
import com.uit.daniel.hotsalesmanager.utils.ProductManagerUtils
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.view.custom.userproducts.UserProductsAdapter
import com.uit.daniel.hotsalesmanager.view.product.productdetail.ProductDetailActivity
import com.uit.daniel.hotsalesmanager.view.salesmanager.SalesManagerViewModel
import kotlinx.android.synthetic.main.fragment_navigation_user_product.*

class UserProductFragment : Fragment() {

    private lateinit var salesManagerViewModel: SalesManagerViewModel
    private lateinit var userManagerUtil: UserManagerUtil
    private lateinit var userProductsAdapter: UserProductsAdapter
    private var products = ArrayList<ProductResult>()
    private var productManagerUtils = ProductManagerUtils()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_user_product, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProducts()
    }

    @SuppressLint("CheckResult")
    private fun showProducts() {
        salesManagerViewModel.userProductsObservable().subscribe { productRespone ->
            products = productManagerUtils.getProductsNotEcommerce(productRespone.result as ArrayList<ProductResult>)

            userProductsAdapter = UserProductsAdapter(products, object : UserProductsAdapter.OnItemClickedListener {
                override fun onItemClicked(id: String) {
                    startProductDetailActivity(id)
                }
            })
            setProductsView()
        }
        salesManagerViewModel.userProducts(userManagerUtil.getUserId())
    }

    private fun startProductDetailActivity(id: String) {
        val intent = Intent(activity, ProductDetailActivity::class.java)
        intent.putExtra("ID", id)
        activity.startActivity(intent)
    }

    private fun setProductsView() {
        rvProducts.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = userProductsAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        salesManagerViewModel = SalesManagerViewModel(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
    }

    override fun onResume() {
        super.onResume()
        showProducts()
    }

}