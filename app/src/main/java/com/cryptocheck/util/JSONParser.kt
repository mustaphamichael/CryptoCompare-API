package com.cryptocheck.util

import com.cryptocheck.model.CryptoCurrency
import com.cryptocheck.model.Currency
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment.Companion.BTC
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment.Companion.ETH
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by mmustapha on 11/3/17.
 */
object JSONParser {
    private var gson: Gson
    val gsonBuilder = GsonBuilder()
    var parsedResponseString: String? = null

    init {
        gsonBuilder.setDateFormat("d/M/yy hh:mm a")
        gson = gsonBuilder.create()
    }

    /**
     * Parse the JSON and return a Currency object
     * @param serverResponse
     * @param cryptoCurrency
     * @return
     */
    fun parseJSON(serverResponse: String, cryptoCurrency: String): Currency {
        val parsedResponse = gson.fromJson(serverResponse, CryptoCurrency::class.java)
        when (cryptoCurrency) {
            BTC -> {
                parsedResponseString = parsedResponse.BTC.toString()
            }
            ETH -> {
                parsedResponseString = parsedResponse.ETH.toString()
            }
        }
        return gson.fromJson(parsedResponseString, Currency::class.java)
    }
}