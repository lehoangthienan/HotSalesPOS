package com.uit.daniel.hotsalesmanager.view.custom.productadded

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.model.Product

class ProductAddedAdapter(
    private var products: ArrayList<Product>
) : RecyclerView.Adapter<ProductAddedViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAddedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_added_cart, parent, false)
        context = parent.context
        return ProductAddedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductAddedViewHolder, position: Int) {
        holder.bindData(context, products[position])
    }
}
