package com.uit.daniel.hotsalesmanager.view.custom.userproducts

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResult

class UserProductsAdapter (
    private var products: ArrayList<ProductResult>,
    private var onItemClickedListener: OnItemClickedListener,
    private var onDeleteClickedListener: OnDeleteClickedListener,
    private var onUpdateClickedListener: OnUpdateClickedListener
) : RecyclerView.Adapter<UserProductsViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_product, parent, false)
        context = parent.context
        return UserProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: UserProductsViewHolder, position: Int) {
        holder.bindData(
            context,
            products[position],
            onItemClickedListener,
            onDeleteClickedListener,
            onUpdateClickedListener
        )
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