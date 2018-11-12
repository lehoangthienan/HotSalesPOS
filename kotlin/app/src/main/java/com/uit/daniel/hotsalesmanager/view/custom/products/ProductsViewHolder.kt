package com.uit.daniel.hotsalesmanager.view.custom.products

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.data.response.ProductResult
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import com.uit.daniel.hotsalesmanager.utils.getVisibilityView
import kotlinx.android.synthetic.main.item_products.view.*

class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val priceUtils = PriceUtils()
    fun bindData(
        context: Context,
        product: ProductResult,
        onItemClickedListener: ProductsAdapter.OnItemClickedListener
    ) {
        loadImage(context, product, itemView)
        loadText(product, itemView)
        loadAnimationForSale(itemView, product)
        addEvents(itemView, onItemClickedListener, product)
    }

    private fun loadAnimationForSale(itemView: View, product: ProductResult) {
        if (product.isWebsite!!) {
            val anim = RotateAnimation(0f, 350f, 50f, 50f)
            anim.interpolator = LinearInterpolator()
            anim.repeatCount = Animation.INFINITE
            anim.duration = 700
            itemView.ivIcHotSale.startAnimation(anim)
        } else itemView.ivIcHotSale.visibility = getVisibilityView(false)
    }

    private fun addEvents(
        itemView: View,
        onItemClickedListener: ProductsAdapter.OnItemClickedListener,
        product: ProductResult
    ) {
        itemView.cvItemProduct.setOnClickListener {
            onItemClickedListener.onItemClicked(product.id.toString())
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
        itemView.tvBranchName.text = product.owner
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