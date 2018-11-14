package com.uit.daniel.hotsalesmanager.view.custom.orders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.OrderResult

class OrderAdapter(
    private var orders: ArrayList<OrderResult>?,
    private var onItemClickedListener: OnItemClickedListener,
    private var onDeleteClickedListener: OnDeleteClickedListener,
    private var onUpdateClickedListener: OnUpdateClickedListener
) : RecyclerView.Adapter<OrderViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_product, parent, false)
        context = parent.context
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (orders == null) return 0
        else return orders?.size!!
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        if (orders != null) {
            holder.bindData(
                context,
                orders!![position],
                onItemClickedListener,
                onDeleteClickedListener,
                onUpdateClickedListener
            )
        }
    }

    interface OnItemClickedListener {
        fun onItemClicked(id: String)
    }

    interface OnDeleteClickedListener {
        fun onDeleteClickedListener(id: String)
    }

    interface OnUpdateClickedListener {
        fun onUpdateClickedListener(id: String)
    }
}