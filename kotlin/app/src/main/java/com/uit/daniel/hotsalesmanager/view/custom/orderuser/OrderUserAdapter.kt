package com.uit.daniel.hotsalesmanager.view.custom.orderuser

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.OrderResult

class OrderUserAdapter (
    private var orders: ArrayList<OrderResult>?,
    private var onItemClickedListener: OnItemClickedListener,
    private var onDeleteClickedListener: OnDeleteClickedListener
) : RecyclerView.Adapter<OrderUserViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_user, parent, false)
        context = parent.context
        return OrderUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (orders == null) return 0
        else return orders?.size!!
    }

    override fun onBindViewHolder(holder: OrderUserViewHolder, position: Int) {
        if (orders != null) {
            holder.bindData(
                context,
                orders!![orders!!.size - 1 - position],
                onItemClickedListener,
                onDeleteClickedListener
            )
        }
    }

    interface OnItemClickedListener {
        fun onItemClicked(id: String)
    }

    interface OnDeleteClickedListener {
        fun onDeleteClickedListener(id: String)
    }
}