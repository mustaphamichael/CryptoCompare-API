package com.cryptocheck.view.main

import com.cryptocheck.model.ExchangeRate

/**
 * Created by mmustapha on 11/5/17.
 */
interface ToggleFragments {
    fun toExchangeRateFragment(cryptoCurrency: String)
    fun toConversionFragment(cryptoCurrency: String, exchangeRate: ExchangeRate)
}