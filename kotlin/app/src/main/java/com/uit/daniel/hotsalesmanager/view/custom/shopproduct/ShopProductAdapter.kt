package com.uit.daniel.hotsalesmanager.view.custom.shopproduct

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResult

class ShopProductAdapter(
    private var products: ArrayList<ProductResult>,
    private var onItemClickedListener: OnItemClickedListener,
    private var onCallClickedListener: OnCallClickedListener,
    private var onSmsClickedListener: OnSmsClickedListener
) : RecyclerView.Adapter<ShopProductViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop_product, parent, false)
        context = parent.context
        return ShopProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ShopProductViewHolder, position: Int) {
        holder.bindData(
            context,
            products[position],
            onItemClickedListener,
            onCallClickedListener,
            onSmsClickedListener
        )
    }

    interface OnItemClickedListener {
        fun onItemClicked(id: String)
    }

    interface OnCallClickedListener {
        fun onCallClickedListener(phoneNumber: String)
    }

    interface OnSmsClickedListener {
        fun onSmsClickedListener(phoneNumber: String)
    }
}