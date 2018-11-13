package com.uit.daniel.hotsalesmanager.view.custom.userproducts

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.data.response.ProductResult
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import kotlinx.android.synthetic.main.item_user_product.view.*

class UserProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val priceUtils = PriceUtils()
    fun bindData(
        context: Context,
        product: ProductResult,
        onItemClickedListener: UserProductsAdapter.OnItemClickedListener,
        onDeleteClickedListener: UserProductsAdapter.OnDeleteClickedListener,
        onUpdateClickedListener: UserProductsAdapter.OnUpdateClickedListener
    ) {
        loadImage(context, product, itemView)
        loadText(product, itemView)
        addEvents(itemView, onItemClickedListener, product, onDeleteClickedListener, onUpdateClickedListener)
    }

    private fun addEvents(
        itemView: View,
        onItemClickedListener: UserProductsAdapter.OnItemClickedListener,
        product: ProductResult,
        onDeleteClickedListener: UserProductsAdapter.OnDeleteClickedListener,
        onUpdateClickedListener: UserProductsAdapter.OnUpdateClickedListener
    ) {
        itemView.cvItemProduct.setOnClickListener {
            onItemClickedListener.onItemClicked(product.id.toString())
        }
        itemView.viewDelete.setOnClickListener {
            onDeleteClickedListener.onDeleteClickedListener(product.id.toString())
        }
        itemView.viewUpdate.setOnClickListener {
            onUpdateClickedListener.onUpdateClickedListener(product.id.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadText(product: ProductResult, itemView: View) {
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
        itemView.tvBranchName.text = product.owner?.name
    }

    private fun loadImage(context: Context, product: ProductResult, itemView: View?) {
        itemView?.ivProduct?.let {
            Glide.with(context)
                .asBitmap()
                .load(product.image)
                .into(it)
        }
    }
}