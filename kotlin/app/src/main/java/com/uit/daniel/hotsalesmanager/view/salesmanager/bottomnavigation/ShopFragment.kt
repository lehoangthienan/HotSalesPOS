package com.uit.daniel.hotsalesmanager.view.salesmanager.bottomnavigation

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.jakewharton.rxbinding2.widget.RxTextView
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResult
import com.uit.daniel.hotsalesmanager.utils.ProductManagerUtils
import com.uit.daniel.hotsalesmanager.utils.getVisibilityView
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
            if (productRespone.result != null) {
                products =
                        productManagerUtils.getProductsNotEcommerce(productRespone.result as ArrayList<ProductResult>)

                Log.d("CountXXX", products.size.toString())
                productsAdapter = ShopProductAdapter(products, object : ShopProductAdapter.OnItemClickedListener {
                    override fun onItemClicked(id: String) {
                        startProductDetailActivity(id)
                    }
                }, object : ShopProductAdapter.OnCallClickedListener {
                    override fun onCallClickedListener(phoneNumber: String) {
                        call(phoneNumber)
                    }
                }, object : ShopProductAdapter.OnSmsClickedListener {
                    override fun onSmsClickedListener(productName: String, phoneNumber: String) {
                        sms(productName, phoneNumber)
                    }
                })
                setProductsView()
            }
        }
        salesManagerViewModel.products()
    }

    private fun sms(productName: String, phoneNumber: String) {
        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.data = Uri.parse("sms:");
        smsIntent.putExtra("address", phoneNumber)
        smsIntent.putExtra(
            "sms_body",
            "I want to buy $productName you are selling. Please tell me if it's still available?"
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
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setProductsView() {
        try {
            rvProducts.apply {
                this.layoutManager = LinearLayoutManager(activity)
                this.adapter = productsAdapter
                if (progressBarAddLocation != null) progressBarAddLocation.visibility = getVisibilityView(false)
                if (swipeContainer != null) swipeContainer.isRefreshing = false
            }
        } catch (e: Exception) {
        }
    }

    @SuppressLint("CheckResult")
    private fun addEvents() {
        tvCategory.setOnClickListener {
            val popup = PopupMenu(activity, tvCategory)
            popup.menuInflater.inflate(R.menu.categoryseller, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.house -> setProducts(0)
                    R.id.moto -> setProducts(1)
                    R.id.electric -> setProducts(2)
                    R.id.mother -> setProducts(3)
                    R.id.inter -> setProducts(4)
                    R.id.fashtion -> setProducts(5)
                    R.id.sport -> setProducts(6)
                    R.id.pet -> setProducts(7)
                    R.id.all -> setProducts(8)
                    R.id.fivekm -> filter5km()
                    R.id.tenkm -> filter10km()
                    R.id.fiteenkm -> filter15km()
                }
                true
            }
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
                        override fun onSmsClickedListener(productName: String, phoneNumber: String) {
                            sms(productName, phoneNumber)
                        }
                    })
                setProductsView()
            }
        }
        swipeContainer.setOnRefreshListener {
            showProducts()
        }
    }

    private fun filter5km() {
        getProductsWithRadius(5)
    }

    private fun filter10km() {
        getProductsWithRadius(10)
    }

    private fun filter15km() {
        getProductsWithRadius(15)
    }

    private fun getProductsWithRadius(radius: Int) {
        productsAdapter = ShopProductAdapter(
            productManagerUtils.getProductsDistance(products, radius, activity),
            object : ShopProductAdapter.OnItemClickedListener {
                override fun onItemClicked(id: String) {
                    startProductDetailActivity(id)
                }
            }, object : ShopProductAdapter.OnCallClickedListener {
                override fun onCallClickedListener(phoneNumber: String) {
                    call(phoneNumber)
                }
            }, object : ShopProductAdapter.OnSmsClickedListener {
                override fun onSmsClickedListener(productName: String, phoneNumber: String) {
                    sms(productName, phoneNumber)
                }
            })
        setProductsView()
    }

    private fun setProducts(positionCategory: Int) {
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
                override fun onSmsClickedListener(productName: String, phoneNumber: String) {
                    sms(productName, phoneNumber)
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