package com.uit.daniel.hotsalesmanager.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class PriceUtils {

    fun priceDiscount(percent: Int, price: Int): Int {
        return price * percent / 100
    }

    fun setStringMoney(price: Int): String {
        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator = ','
        val decimalFormat = DecimalFormat("đ ###,###,###,###", symbols)
        val prezzo = decimalFormat.format(Integer.parseInt(price.toString()))
        return prezzo
    }
}