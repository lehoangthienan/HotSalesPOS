package com.uit.daniel.hotsalesmanager.view.salesmanager

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
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.view.custom.products.ProductsAdapter
import com.uit.daniel.hotsalesmanager.view.signin.signinwithfacebook.SignInFacebookActivity
import kotlinx.android.synthetic.main.fragment_sales_manager.*
import kotlinx.android.synthetic.main.navigation_sales_manager.*

class SalesManagerFragment : Fragment() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var userManagerUtil: UserManagerUtil
    private var products = ArrayList<Product>()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sales_manager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFakeData()
        addControls()
        setProductsAdapter()
        setProductsView()
        addEvents()
    }

    private fun setProductsView() {
        rvProducts.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = productsAdapter
        }
    }

    private fun setProductsAdapter() {
        productsAdapter = ProductsAdapter(products, object : ProductsAdapter.OnItemClickedListener {
            override fun onItemClicked(id: String) {
                startProductDetailActivity(id)
            }
        })
    }

    private fun startProductDetailActivity(id: String) {

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
        products.add(
            Product(
                "1",
                "Iphone Xs Max",
                15000000,
                50,
                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2018/09/danh-gia-iphone-xs-max-12.jpg",
                "ChuGiong"
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
                "Shopee"
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
                "TiKi"
            )
        )

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
    }
}