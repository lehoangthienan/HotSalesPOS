package com.uit.daniel.hotsalesmanager.view.salesmanager.bottomnavigation

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.jakewharton.rxbinding2.widget.RxTextView
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResult
import com.uit.daniel.hotsalesmanager.utils.ProductManagerUtils
import com.uit.daniel.hotsalesmanager.view.custom.shopproduct.ShopProductAdapter
import com.uit.daniel.hotsalesmanager.view.product.productdetail.ProductDetailActivity
import com.uit.daniel.hotsalesmanager.view.salesmanager.SalesManagerViewModel
import kotlinx.android.synthetic.main.fragment_navigation_shop.*


class ShopFragment : Fragment() {

    private lateinit var salesManagerViewModel: SalesManagerViewModel
    private var positionCategory: Int = 8
    private lateinit var productsAdapter: ShopProductAdapter
    private var products = ArrayList<ProductResult>()
    private var productManagerUtils = ProductManagerUtils()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_shop, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProducts()
        addEvents()

    }

    @SuppressLint("CheckResult")
    private fun showProducts() {
        salesManagerViewModel.productsObservable().subscribe { productRespone ->
            products = productManagerUtils.getProductsNotEcommerce(productRespone.result as ArrayList<ProductResult>)

            productsAdapter = ShopProductAdapter(products, object : ShopProductAdapter.OnItemClickedListener {
                override fun onItemClicked(id: String) {
                    startProductDetailActivity(id)
                }
            }, object : ShopProductAdapter.OnCallClickedListener {
                override fun onCallClickedListener(phoneNumber: String) {
                    call(phoneNumber)
                }
            }, object : ShopProductAdapter.OnSmsClickedListener {
                override fun onSmsClickedListener(phoneNumber: String) {
                    sms(phoneNumber)
                }
            })
            setProductsView()
        }
        salesManagerViewModel.products()
    }

    private fun sms(phoneNumber: String) {
        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.data = Uri.parse("sms:");
        smsIntent.putExtra("address", phoneNumber)
        smsIntent.putExtra(
            "sms_body",
            "I want to buy the product you are selling. Please tell me if it's still available?"
        )
        startActivity(smsIntent)
    }

    private fun call(phoneNumber: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)))
    }

    private fun startProductDetailActivity(id: String) {
        val intent = Intent(activity, ProductDetailActivity::class.java)
        intent.putExtra("ID", id)
        activity.startActivity(intent)
    }

    private fun setProductsView() {
        rvProducts.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = productsAdapter
        }
    }

    @SuppressLint("CheckResult")
    private fun addEvents() {
        tvCategory.setOnClickListener {
            val popup = PopupMenu(activity, tvCategory)
            popup.menuInflater.inflate(R.menu.category, popup.menu)
            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.house -> positionCategory = 0
                        R.id.moto -> positionCategory = 1
                        R.id.electric -> positionCategory = 2
                        R.id.mother -> positionCategory = 3
                        R.id.inter -> positionCategory = 4
                        R.id.fashtion -> positionCategory = 5
                        R.id.sport -> positionCategory = 6
                        R.id.pet -> positionCategory = 7
                        R.id.all -> positionCategory = 8
                    }
                    setProducts()
                    return true
                }

            })
            popup.show()
        }
        RxTextView.textChanges(etSearch).subscribe { key ->
            if (key.isNullOrBlank()) showProducts()
            else {
                productsAdapter = ShopProductAdapter(
                    productManagerUtils.fillerProductsForKey(products, key.toString()),
                    object : ShopProductAdapter.OnItemClickedListener {
                        override fun onItemClicked(id: String) {
                            startProductDetailActivity(id)
                        }
                    }, object : ShopProductAdapter.OnCallClickedListener {
                        override fun onCallClickedListener(phoneNumber: String) {
                            call(phoneNumber)
                        }
                    }, object : ShopProductAdapter.OnSmsClickedListener {
                        override fun onSmsClickedListener(phoneNumber: String) {
                            sms(phoneNumber)
                        }
                    })
                setProductsView()
            }
        }
    }

    private fun setProducts() {
        productsAdapter = ShopProductAdapter(
            productManagerUtils.getProductsForCategory(products, positionCategory),
            object : ShopProductAdapter.OnItemClickedListener {
                override fun onItemClicked(id: String) {
                    startProductDetailActivity(id)
                }
            }, object : ShopProductAdapter.OnCallClickedListener {
                override fun onCallClickedListener(phoneNumber: String) {
                    call(phoneNumber)
                }
            }, object : ShopProductAdapter.OnSmsClickedListener {
                override fun onSmsClickedListener(phoneNumber: String) {
                    sms(phoneNumber)
                }
            })
        setProductsView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        salesManagerViewModel = SalesManagerViewModel(context)
    }

    override fun onResume() {
        super.onResume()
        showProducts()
    }

}