package com.uit.daniel.hotsalesmanager.view.order.updateorder

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
import com.uit.daniel.hotsalesmanager.data.response.OrderResponse
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import com.uit.daniel.hotsalesmanager.utils.ToastSnackBar
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.utils.getVisibilityView
import com.uit.daniel.hotsalesmanager.view.location.searchaddresslocation.SearchAddressLocationActivity
import com.uit.daniel.hotsalesmanager.view.product.productdetail.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_update_order.*

class UpdateOrderFragment : Fragment() {

    private var isUpdate = false
    private var orderId = ""
    private var productId = ""
    private lateinit var updateOrderViewModel: UpdateOrderViewModel
    private val priceUtils = PriceUtils()
    private lateinit var userManagerUtil: UserManagerUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update_order, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getOrderId()
        getOrderSever()
        initView()
        addEvents()
    }

    @SuppressLint("CheckResult")
    private fun getOrderSever() {
        updateOrderViewModel.ordersObservable().subscribe { orderResponse ->
            initeView(orderResponse)
        }
        updateOrderViewModel.orders(orderId)
    }

    @SuppressLint("SetTextI18n")
    private fun initeView(orderResponse: OrderResponse?) {
        productId = orderResponse?.result?.get(0)?.product?.id.toString()
        etName.setText(orderResponse?.result?.get(0)?.name)
        etAddress.setText(orderResponse?.result?.get(0)?.address)
        etPhoneNumber.setText(orderResponse?.result?.get(0)?.phonenumber)
        tvTitleName.text = orderResponse?.result?.get(0)?.product?.name
        tvName.text = orderResponse?.result?.get(0)?.product?.name
        tvPrice.text = orderResponse?.result?.get(0)?.product?.price?.let { priceUtils.setStringMoney(it) }
        tvPriceDiscount.text = priceUtils.setStringMoney(orderResponse?.result?.get(0)?.product?.discount?.let {
            orderResponse.result?.get(0)?.product?.price?.let { it1 ->
                priceUtils.priceDiscount(
                    it,
                    it1
                )
            }
        }!!)
        tvPercentDiscount.text = orderResponse.result?.get(0)?.product?.discount.toString() + "%"
        tvBranchName.text = orderResponse.result?.get(0)?.ownernameproduct

        try {
            ivProduct?.let {
                Glide.with(activity)
                    .asBitmap()
                    .load(orderResponse.result?.get(0)?.product?.image)
                    .into(it)
            }
        } catch (e: Exception) {
        }
    }

    private fun initView() {
        if (!isUpdate) {
            tvFinish.visibility = getVisibilityView(false)
            etName.isFocusable = false
            etAddress.isFocusable = false
            etPhoneNumber.isFocusable = false
            ivAddLocation.isEnabled = false
        }
    }

    private fun addEvents() {
        tvBack.setOnClickListener {
            activity.finish()
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvFinish.setOnClickListener {
            isFullInformation()
        }
        ivAddLocation.setOnClickListener {
            startSearchLocationActivity()
        }
        cvItemProduct.setOnClickListener {
            startProductDetailActivity()
        }
    }

    private fun startProductDetailActivity() {
        val intent = Intent(activity, ProductDetailActivity::class.java)
        intent.putExtra("ID", productId)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setLatLagOfProduct() {
        userManagerUtil.setLat(0.0)
        userManagerUtil.setLng(0.0)
    }


    private fun startSearchLocationActivity() {
        val intent = Intent(activity, SearchAddressLocationActivity::class.java)
        activity.startActivity(intent)
    }

    private fun isFullInformation() {
        if (etName.text.toString().isNullOrBlank() || etAddress.text.toString().isNullOrBlank() || etPhoneNumber.text.toString().isNullOrBlank()) ToastSnackBar.showSnackbar(
            "Please enter your personal details!",
            view,
            activity
        )
        else updateOrderToSever()
    }

    @SuppressLint("CheckResult")
    private fun updateOrderToSever() {
        updateOrderViewModel.updateOrderObservable().subscribe { check ->
            if (check) {
                setLatLagOfProduct()
                userManagerUtil.setAddressLocation("")
                activity.finish()
                activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }

        updateOrderViewModel.updateOrder(
            orderId,
            productId,
            etName.text.toString(),
            etAddress.text.toString(),
            etPhoneNumber.text.toString(),
            tvBranchName.text.toString()
        )
    }

    private fun getOrderId() {
        orderId = activity?.intent?.getStringExtra("ID") ?: ""
        isUpdate = activity?.intent?.getBooleanExtra("isUpdate", false) ?: false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        updateOrderViewModel = UpdateOrderViewModel(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
    }

    override fun onResume() {
        super.onResume()
        if (!userManagerUtil.getAddressLocation().isNullOrBlank()) etAddress.setText(userManagerUtil.getAddressLocation())
    }
}