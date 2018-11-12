package com.uit.daniel.hotsalesmanager.view.salesmanager

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.model.Product
import com.uit.daniel.hotsalesmanager.utils.Constant.NAME_USER_DEFAULT
import com.uit.daniel.hotsalesmanager.utils.ToastSnackBar
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.view.custom.products.ProductsAdapter
import com.uit.daniel.hotsalesmanager.view.product.createproduct.CreateProductActivity
import com.uit.daniel.hotsalesmanager.view.product.productaddedcart.ProductAddedCartActivity
import com.uit.daniel.hotsalesmanager.view.product.productdetail.ProductDetailActivity
import com.uit.daniel.hotsalesmanager.view.signin.signinwithfacebook.SignInFacebookActivity
import kotlinx.android.synthetic.main.fragment_sales_manager.*
import kotlinx.android.synthetic.main.navigation_sales_manager.*

class SalesManagerFragment : Fragment() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var userManagerUtil: UserManagerUtil
    private var products = ArrayList<Product>()
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var salesManagerViewModel: SalesManagerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sales_manager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProducts()
        addControls()
        addEvents()
    }

    @SuppressLint("CheckResult")
    private fun getProducts() {
        salesManagerViewModel.productsObservable().subscribe { productRespone ->
            productsAdapter = ProductsAdapter(productRespone, object : ProductsAdapter.OnItemClickedListener {
                override fun onItemClicked(id: String) {
                    startProductDetailActivity(id)
                }
            })
            setProductsView()
        }
        salesManagerViewModel.products()
    }

    private fun setProductsView() {
        rvProducts.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = productsAdapter
        }
    }

    private fun startProductDetailActivity(id: String) {
        val intent = Intent(activity, ProductDetailActivity::class.java)
        intent.putExtra("ID", id)
        activity.startActivity(intent)
    }

    private fun addEvents() {
        tvMenu.setOnClickListener {
            activity_main_drawer.openDrawer(GravityCompat.START)
        }
        tvExit.setOnClickListener {
            activity.finish()
        }
        tvSignin.setOnClickListener {
            startLogInWithFacebookActivity()
        }
        fabAddProduct.setOnClickListener {
            if (tvUserName.text.toString() == NAME_USER_DEFAULT) ToastSnackBar.showSnackbar(
                "Please sign in before doing this.",
                view,
                activity
            )
            else startCreateProduct()
        }
        ivShoppingCart.setOnClickListener {
            startProductAddedCart()
        }
    }

    private fun startProductAddedCart() {
        val intent = Intent(activity, ProductAddedCartActivity::class.java)
        activity.startActivity(intent)
    }

    private fun startCreateProduct() {
        val intent = Intent(activity, CreateProductActivity::class.java)
        activity.startActivity(intent)
    }

    private fun startLogInWithFacebookActivity() {
        val intent = Intent(activity, SignInFacebookActivity::class.java)
        startActivity(intent)
    }

    private fun addControls() {
        drawerToggle = ActionBarDrawerToggle(
            activity,
            activity_main_drawer,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        activity_main_drawer.addDrawerListener(drawerToggle)

        nvvSalesManager.background.setColorFilter(0x800000, PorterDuff.Mode.MULTIPLY)
    }

    override fun onResume() {
        super.onResume()
        try {
            setUserImage()
            setUserName()
            getProducts()
            setProductsView()
        } catch (e: Exception) {
        }
    }

    private fun setUserName() {
        tvUserName.text = userManagerUtil.getUserName()
    }

    private fun setUserImage() {
        imUserAvatar?.let {
            Glide.with(activity)
                .asBitmap()
                .load(userManagerUtil.getUrlkUserAvatar())
                .into(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userManagerUtil = UserManagerUtil(context)
        salesManagerViewModel = SalesManagerViewModel(context)
    }
}