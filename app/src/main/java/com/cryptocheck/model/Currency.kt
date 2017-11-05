package com.cryptocheck.model

import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by mmustapha on 11/2/17.
 */

data class Currency(

        val NGN: Double,
        val USD: Double,
        val EUR: Double,
        val GBP: Double,
        val JPY: Double,
        val CNY: Double,
        val CAD: Double,
        val AUD: Double,
        val CHF: Double,
        val SGD: Double,
        val INR: Double,
        val MYR: Double,
        val KES: Double,
        val ZAR: Double,
        val GHS: Double,
        val RUB: Double,
        val MXN: Double,
        val QAR: Double,
        val EGP: Double,
        val ILS: Double
)