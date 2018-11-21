package com.uit.daniel.hotsalesmanager.view.custom.shopproduct

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.data.response.ProductResult
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import kotlinx.android.synthetic.main.item_shop_product.view.*

class ShopProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val priceUtils = PriceUtils()
    fun bindData(
        context: Context,
        product: ProductResult,
        onItemClickedListener: ShopProductAdapter.OnItemClickedListener,
        onCallClickedListener: ShopProductAdapter.OnCallClickedListener,
        onSmsClickedListener: ShopProductAdapter.OnSmsClickedListener
    ) {
        loadImage(context, product, itemView)
        loadText(product, itemView)
        loadAnimationForSale(context, itemView, product)
        addEvents(itemView, onItemClickedListener, onCallClickedListener, product, onSmsClickedListener, context)
    }

    private fun loadAnimationForSale(context: Context, itemView: View, product: ProductResult) {
        if (product.isWebsite!!) {
            val anim = RotateAnimation(0f, 350f, 50f, 50f)
            anim.interpolator = LinearInterpolator()
            anim.repeatCount = Animation.INFINITE
            anim.duration = 700
            itemView.ivIcHotSale.startAnimation(anim)
        } else {
            itemView.ivIcHotSale.let {
                Glide.with(context)
                    .asBitmap()
                    .load(product.owner?.avatar)
                    .into(it)
            }
        }
    }

    private fun addEvents(
        itemView: View,
        onItemClickedListener: ShopProductAdapter.OnItemClickedListener,
        onCallClickedListener: ShopProductAdapter.OnCallClickedListener,
        product: ProductResult,
        onSmsClickedListener: ShopProductAdapter.OnSmsClickedListener,
        context: Context
    ) {
        itemView.viewItemProduct.setOnClickListener {
            onItemClickedListener.onItemClicked(product.id.toString())
        }
        itemView.viewCall.setOnClickListener {
            if (product.owner?.phone_number.isNullOrBlank()) Toast.makeText(
                context,
                "The seller does not leave the contact phone number.",
                Toast.LENGTH_LONG
            ).show()
            else onCallClickedListener.onCallClickedListener(product.owner?.phone_number!!)

        }
        itemView.viewSms.setOnClickListener {
            if (product.owner?.phone_number.isNullOrBlank()) Toast.makeText(
                context,
                "The seller does not leave the contact phone number.",
                Toast.LENGTH_LONG
            ).show()
            else product.name?.let { it1 ->
                onSmsClickedListener.onSmsClickedListener(
                    it1,
                    product.owner?.phone_number!!
                )
            }
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