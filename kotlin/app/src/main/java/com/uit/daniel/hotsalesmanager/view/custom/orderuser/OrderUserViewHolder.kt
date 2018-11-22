package com.uit.daniel.hotsalesmanager.view.custom.orderuser

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.uit.daniel.hotsalesmanager.data.response.OrderResult
import com.uit.daniel.hotsalesmanager.utils.PriceUtils
import kotlinx.android.synthetic.main.item_order_user.view.*

class OrderUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val priceUtils = PriceUtils()
    fun bindData(
        context: Context,
        orderResult: OrderResult,
        onItemClickedListener: OrderUserAdapter.OnItemClickedListener,
        onDeleteClickedListener: OrderUserAdapter.OnDeleteClickedListener
    ) {
        loadImage(context, orderResult, itemView)
        loadText(orderResult, itemView)
        addEvents(itemView, onItemClickedListener, orderResult, onDeleteClickedListener)
    }

    private fun addEvents(
        itemView: View,
        onItemClickedListener: OrderUserAdapter.OnItemClickedListener,
        orderResult: OrderResult,
        onDeleteClickedListener: OrderUserAdapter.OnDeleteClickedListener
    ) {
        itemView.cvItemProduct.setOnClickListener {
            onItemClickedListener.onItemClicked(orderResult.id.toString())
        }
        itemView.viewDelete.setOnClickListener {
            onDeleteClickedListener.onDeleteClickedListener(orderResult.id.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadText(orderResult: OrderResult, itemView: View) {
        itemView.tvName.text = orderResult.product?.name
        itemView.tvPrice.text = orderResult.product?.price?.let { priceUtils.setStringMoney(it) }
        itemView.tvPriceDiscount.text = priceUtils.setStringMoney(orderResult.product?.discount?.let {
            orderResult.product?.price?.let { it1 ->
                priceUtils.priceDiscount(
                    it,
                    it1
                )
            }
        }!!)
        itemView.tvPercentDiscount.text = orderResult.product?.discount.toString() + "%"
        itemView.tvBranchName.text = orderResult.ownernameproduct
    }

    private fun loadImage(context: Context, orderResult: OrderResult, itemView: View?) {
        itemView?.ivProduct?.let {
            Glide.with(context)
                .asBitmap()
                .load(orderResult.product?.image)
                .into(it)
        }
    }
}