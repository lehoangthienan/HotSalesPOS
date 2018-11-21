package com.uit.daniel.hotsalesmanager.view.order.orderproduct

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import com.uit.daniel.hotsalesmanager.utils.ToastSnackBar
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.view.location.searchaddresslocation.SearchAddressLocationActivity
import kotlinx.android.synthetic.main.fragment_order_product.*

class OrderProductFragment : Fragment() {

    private val priceUtils = PriceUtils()
    private lateinit var userManagerUtil: UserManagerUtil
    private var productId: String = ""
    private lateinit var orderProductViewModel: OrderProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_product, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductId()
        getProductDetail()
        addEvents()
    }

    private fun getProductId() {
        productId = activity?.intent?.getStringExtra("ID") ?: ""
    }

    private fun addEvents() {
        ivAddLocation.setOnClickListener {
            startSearchLocationActivity()
        }
        tvBack.setOnClickListener {
            userManagerUtil.setAddressLocation("")
            activity.finish()
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvCheckFinish.setOnClickListener {
            isFullInformation()
        }
    }

    private fun setLatLagOfProduct() {
        userManagerUtil.setLat(0.0)
        userManagerUtil.setLng(0.0)
    }

    private fun isFullInformation() {
        if (etName.text.toString().isNullOrBlank() || etAddress.text.toString().isNullOrBlank() || etPhoneNumber.text.toString().isNullOrBlank()) ToastSnackBar.showSnackbar(
            "Please enter your personal details!",
            view,
            activity
        )
        else createOrderToSever()
    }

    @SuppressLint("CheckResult")
    private fun createOrderToSever() {
        orderProductViewModel.createOrderObservable().subscribe { check ->
            if (check) {
                setLatLagOfProduct()
                userManagerUtil.setAddressLocation("")
                activity.finish()
                activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }
        orderProductViewModel.createOrder(
            productId,
            etName.text.toString(),
            etAddress.text.toString(),
            etPhoneNumber.text.toString()
        )
    }

    private fun startSearchLocationActivity() {
        val intent = Intent(activity, SearchAddressLocationActivity::class.java)
        activity.startActivity(intent)
    }

    private fun initView(productRespnse: ProductResponse) {
        tvName.text = productRespnse.result?.get(0)?.name
        tvPrice.text = productRespnse.result?.get(0)?.price?.let { priceUtils.setStringMoney(it) }
        tvPriceDiscount.text = priceUtils.setStringMoney(productRespnse.result?.get(0)?.discount?.let {
            productRespnse.result?.get(0)?.price?.let { it1 ->
                priceUtils.priceDiscount(
                    it,
                    it1
                )
            }
        }!!)
        tvPercentDiscount.text = productRespnse.result?.get(0)?.discount.toString() + "%"
        tvBranchName.text = productRespnse.result?.get(0)?.owner?.name

        try {
            ivProduct?.let {
                Glide.with(activity)
                    .asBitmap()
                    .load(productRespnse.result?.get(0)?.image)
                    .into(it)
            }
        } catch (e: Exception) {
        }

    }

    override fun onResume() {
        super.onResume()
        if (!userManagerUtil.getAddressLocation().isNullOrBlank()) etAddress.setText(userManagerUtil.getAddressLocation())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
        orderProductViewModel = OrderProductViewModel(context)
    }

    @SuppressLint("CheckResult")
    private fun getProductDetail() {
        orderProductViewModel.productObservable().subscribe { productRespnse ->
            initView(productRespnse)
        }
        orderProductViewModel.product(productId)
    }
}