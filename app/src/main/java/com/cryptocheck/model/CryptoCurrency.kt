package com.cryptocheck.model

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

/**
 * Created by mmustapha on 11/2/17.
 */

data class CryptoCurrency<T> (
        @SerializedName("BTC")
        val BTC: T,

        @SerializedName("ETH")
        val ETH: T
)