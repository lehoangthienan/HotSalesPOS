package com.uit.daniel.hotsalesmanager.view.custom.products

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.data.model.Product
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import kotlinx.android.synthetic.main.item_products.view.*

class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val priceUtils = PriceUtils()
    fun bindData(context: Context, product: Product, onItemClickedListener: ProductsAdapter.OnItemClickedListener) {
        loadImage(context, product, itemView)
        loadText(product, itemView)
        addEvents(itemView, onItemClickedListener, product)
    }

    private fun addEvents(
        itemView: View,
        onItemClickedListener: ProductsAdapter.OnItemClickedListener,
        product: Product
    ) {
        itemView.cvItemProduct.setOnClickListener {
            onItemClickedListener.onItemClicked(product.id.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadText(product: Product, itemView: View) {
        itemView.tvName.text = product.name
        itemView.tvPrice.text = product.price?.let { priceUtils.setStringMoney(it) }
        itemView.tvPriceDiscount.text = priceUtils.setStringMoney(product.discount?.let {
            product.price?.let { it1 ->
                priceUtils.priceDiscount(
                    it,
                    it1
                )
            }
        }!!)
        itemView.tvPercentDiscount.text = product.discount.toString() + "%"
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