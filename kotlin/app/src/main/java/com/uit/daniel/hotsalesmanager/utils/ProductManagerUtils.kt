package com.uit.daniel.hotsalesmanager.utils

import android.content.Context
import android.location.Location
import android.util.Log
import com.uit.daniel.hotsalesmanager.data.response.ProductResult

class ProductManagerUtils {

    private var locationUtil = LocationUtils.getInstance()

    fun getProductsEcommerce(products: ArrayList<ProductResult>): ArrayList<ProductResult> {
        val productsEcommerce = ArrayList<ProductResult>()
        if (products.isNotEmpty() && products != null) {
            products.forEach { product ->
                if (product.isWebsite!!) productsEcommerce.add(product)
            }
        }
        return productsEcommerce
    }

    fun getProductsDistance(
        products: ArrayList<ProductResult>,
        distance: Int,
        context: Context
    ): ArrayList<ProductResult> {
        val productsDistance = ArrayList<ProductResult>()
        if (products.isNotEmpty() && products != null) {
            products.forEach { product ->
                if (product.lat != null && product.lng != null) {
                    var location = Location("AddressLocation")
                    location.latitude = product.lat!!
                    location.longitude = product.lng!!
                    if (locationUtil.getDistanceLocation(context, location) <= distance * 1000) productsDistance.add(
                        product
                    )
                }
            }
        }
        Log.d("CountXXX", productsDistance.size.toString())
        return productsDistance
    }

    fun getProductsNotEcommerce(products: ArrayList<ProductResult>): ArrayList<ProductResult> {
        val productsNotEcommerce = ArrayList<ProductResult>()
        if (products.isNotEmpty() && products != null) {
            products.forEach { product ->
                if (!product.isWebsite!!) productsNotEcommerce.add(product)
            }
        }
        return productsNotEcommerce
    }

    fun getProductsForCategory(products: ArrayList<ProductResult>, position: Int): ArrayList<ProductResult> {
        if (position == 8) return products
        else {
            val productsNotEcommerce = ArrayList<ProductResult>()
            if (products.isNotEmpty() && products != null) {
                products.forEach { product ->
                    if (product.type == position) productsNotEcommerce.add(product)
                }
            }
            return productsNotEcommerce
        }
    }

    fun fillerProductsForKey(products: ArrayList<ProductResult>, key: String): ArrayList<ProductResult> {
        val productsForKey = ArrayList<ProductResult>()
        if (products.isNotEmpty() && products != null) {
            products.forEach { product ->
                if (product.name!!.toLowerCase().contains(key.toLowerCase())) productsForKey.add(product)
            }
        }
        return productsForKey
    }


}