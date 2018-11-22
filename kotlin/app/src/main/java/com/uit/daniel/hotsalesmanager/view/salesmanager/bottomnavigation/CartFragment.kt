package com.uit.daniel.hotsalesmanager.view.salesmanager.bottomnavigation

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
import com.uit.daniel.hotsalesmanager.utils.UserManagerUtil
import com.uit.daniel.hotsalesmanager.utils.getVisibilityView
import com.uit.daniel.hotsalesmanager.view.custom.orders.OrderAdapter
import com.uit.daniel.hotsalesmanager.view.order.updateorder.UpdateOrderActivity
import com.uit.daniel.hotsalesmanager.view.salesmanager.SalesManagerViewModel
import kotlinx.android.synthetic.main.dialog_delete_product.*
import kotlinx.android.synthetic.main.fragment_navigation_cart.*

class CartFragment : Fragment() {

    private lateinit var salesManagerViewModel: SalesManagerViewModel
    private lateinit var userManagerUtil: UserManagerUtil
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var dlDelete: Dialog
    private var orderIdDelete = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_cart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            salesManagerViewModel.deleteOrder(orderIdDelete)
            dlDelete.dismiss()
        }
        swipeContainer.setOnRefreshListener {
            showOrders()
        }
    }

    private fun initView() {
        dlDelete = Dialog(activity)
        dlDelete.setContentView(R.layout.dialog_delete_product)
        dlDelete.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    @SuppressLint("CheckResult")
    private fun deleteOrder() {
        salesManagerViewModel.deleteProductObservable().subscribe { check ->
            if (check) showOrders()
            else ToastSnackBar.showSnackbar("Delete product failed!", view, activity)
        }
    }

    @SuppressLint("CheckResult")
    private fun showOrders() {
        salesManagerViewModel.ordersObservable().subscribe { orderResponse ->
            if (orderResponse.result != null) {
                orderAdapter = OrderAdapter(
                    orderResponse.result,
                    object : OrderAdapter.OnItemClickedListener {
                        override fun onItemClicked(id: String) {
                            startOrderDetailActivity(id)
                        }
                    }, object : OrderAdapter.OnDeleteClickedListener {
                        override fun onDeleteClickedListener(id: String) {
                            orderIdDelete = id
                            dlDelete.show()
                        }

                    }, object : OrderAdapter.OnUpdateClickedListener {
                        override fun onUpdateClickedListener(id: String) {
                            startUpdateUserOrderActivity(id)
                        }

                    })
                setOrdersView()
            }else {
                progressBarAddLocation.visibility = getVisibilityView(false)
                swipeContainer.isRefreshing = false
            }
        }
        salesManagerViewModel.orders()
    }

    private fun startOrderDetailActivity(id: String) {
        val intent = Intent(activity, UpdateOrderActivity::class.java)
        intent.putExtra("ID", id)
        intent.putExtra("isUpdate", false)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun startUpdateUserOrderActivity(id: String) {
        val intent = Intent(activity, UpdateOrderActivity::class.java)
        intent.putExtra("ID", id)
        intent.putExtra("isUpdate", true)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    private fun setOrdersView() {
        try {
            rvProducts.apply {
                this.layoutManager = LinearLayoutManager(activity)
                this.adapter = orderAdapter
            }
            progressBarAddLocation.visibility = getVisibilityView(false)
            swipeContainer.isRefreshing = false
        } catch (e: Exception) {
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        salesManagerViewModel = SalesManagerViewModel(context)
        userManagerUtil = UserManagerUtil.getInstance(context)
    }

    override fun onResume() {
        super.onResume()
    }

}