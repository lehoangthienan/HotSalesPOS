package com.uit.daniel.hotsalesmanager.view.custom.products

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.data.model.Product
import kotlinx.android.synthetic.main.item_products.view.*

class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(context: Context, product: Product, onItemClickedListener: ProductsAdapter.OnItemClickedListener) {
        loadImage(context, product, itemView)
        loadText(product, itemView)
        addEvents(itemView, onItemClickedListener , product)
    }

    private fun addEvents(itemView: View, onItemClickedListener: ProductsAdapter.OnItemClickedListener, product: Product) {
        itemView.cvItemProduct.setOnClickListener {
            onItemClickedListener.onItemClicked(product.id.toString())
        }
    }

    private fun loadText(product: Product, itemView: View) {
        itemView.tvName.text = product.name
        itemView.tvPrice.text = product.price
        itemView.tvPercentDiscount.text = product.discount
        itemView.tvBranchName.text = product.branch
    }

    private fun loadImage(context: Context, product: Product, itemView: View?) {
        itemView?.ivProduct?.let {
            Glide.with(context)
                .asBitmap()
                .load(product.image)
                .into(it)
        }
    }
}