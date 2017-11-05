package com.cryptocheck.util

import java.text.DecimalFormat

object NumberUtils {

    fun toCurrency(formatInstance: DecimalFormat, money: Double): String {
        val decimalSymbol = formatInstance.decimalFormatSymbols
//        decimalSymbol.currencySymbol = "\u20A6 "
        decimalSymbol.currencySymbol = ""
        formatInstance.decimalFormatSymbols = decimalSymbol
        return formatInstance.format(money)
    }
}