package com.uit.daniel.hotsalesmanager.view.order.orderuser

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.utils.ToastSnackBar
import com.uit.daniel.hotsalesmanager.utils.getVisibilityView
import com.uit.daniel.hotsalesmanager.view.custom.orderuser.OrderUserAdapter
import com.uit.daniel.hotsalesmanager.view.order.updateorder.UpdateOrderActivity
import kotlinx.android.synthetic.main.dialog_delete_product.*
import kotlinx.android.synthetic.main.fragment_order_user.*

class OrderUserFragment : Fragment() {

    private lateinit var orderUserViewModel: OrderUserViewModel
    private lateinit var orderUserAdapter: OrderUserAdapter
    private lateinit var dlDelete: Dialog
    private var orderIdDelete = ""
    private var productId: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_user, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductId()
        initView()
        showOrders()
        deleteOrder()
        addEvents()
    }

    private fun addEvents() {
        dlDelete.tvCancel.setOnClickListener {
            dlDelete.dismiss()
        }
        dlDelete.tvAccept.setOnClickListener {
            orderUserViewModel.deleteOrder(orderIdDelete)
            dlDelete.dismiss()
        }
        swipeContainer.setOnRefreshListener {
            showOrders()
        }
        tvBack.setOnClickListener {
            activity.finish()
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun getProductId() {
        productId = activity?.intent?.getStringExtra("ID") ?: ""
    }

    @SuppressLint("CheckResult")
    private fun deleteOrder() {
        orderUserViewModel.deleteOrderObservable().subscribe { check ->
            if (check) showOrders()
            else ToastSnackBar.showSnackbar("Delete product failed!", view, activity)
        }
    }

    @SuppressLint("CheckResult")
    private fun showOrders() {
        orderUserViewModel.ordersObservable().subscribe { orderResponse ->
            if (orderResponse.result != null) {
                orderUserAdapter = OrderUserAdapter(
                    orderResponse.result,
                    object : OrderUserAdapter.OnItemClickedListener {
                        override fun onItemClicked(id: String) {
                            startOrderDetailActivity(id)
                        }
                    }, object : OrderUserAdapter.OnDeleteClickedListener {
                        override fun onDeleteClickedListener(id: String) {
                            orderIdDelete = id
                            dlDelete.show()
                        }

                    })
                setOrdersView()
            } else {
                progressBarAddLocation.visibility = getVisibilityView(false)
                swipeContainer.isRefreshing = false
            }
        }
        orderUserViewModel.orders(productId)
    }

    private fun startOrderDetailActivity(id: String) {
        val intent = Intent(activity, UpdateOrderActivity::class.java)
        intent.putExtra("ID", id)
        intent.putExtra("isUpdate", false)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setOrdersView() {
        try {
            rvProducts.apply {
                this.layoutManager = LinearLayoutManager(activity)
                this.adapter = orderUserAdapter
            }
            progressBarAddLocation.visibility = getVisibilityView(false)
            swipeContainer.isRefreshing = false
        } catch (e: Exception) {
        }
    }

    private fun initView() {
        dlDelete = Dialog(activity)
        dlDelete.setContentView(R.layout.dialog_delete_product)
        dlDelete.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        orderUserViewModel = OrderUserViewModel(context)
    }
}