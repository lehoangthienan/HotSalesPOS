package com.uit.daniel.hotsalesmanager.utils

import com.uit.daniel.hotsalesmanager.data.response.ProductResult

class ProductManagerUtils {

    fun getProductsEcommerce(products: ArrayList<ProductResult>): ArrayList<ProductResult> {
        val productsEcommerce = ArrayList<ProductResult>()
        if (products.isNotEmpty() && products != null) {
            products.forEach { product ->
                if (product.isWebsite!!) productsEcommerce.add(product)
            }
        }
        return productsEcommerce
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