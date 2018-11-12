package com.uit.daniel.hotsalesmanager.view.custom.products

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uit.daniel.hotsalesmanager.R
import com.uit.daniel.hotsalesmanager.data.response.ProductResponse

class ProductsAdapter(
    private var productResponse: ProductResponse,
    private var onItemClickedListener: OnItemClickedListener
) : RecyclerView.Adapter<ProductsViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false)
        context = parent.context
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (productResponse.result == null) 0
        else productResponse.result!!.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        if (productResponse.result != null) holder.bindData(
            context,
            productResponse.result!![position],
            onItemClickedListener
        )
    }

    interface OnItemClickedListener {
        fun onItemClicked(id: String)
    }
}
