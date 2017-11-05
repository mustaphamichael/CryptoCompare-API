package com.cryptocheck.presenter

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.cryptocheck.R
import com.cryptocheck.contract.ExchangeRateContract
import com.cryptocheck.model.Currency
import com.cryptocheck.model.ExchangeRate
import com.cryptocheck.util.JSONParser
import com.cryptocheck.util.NetworkHelper
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment.Companion.BTC
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment.Companion.ETH
import org.json.JSONObject
import kotlin.collections.ArrayList

/**
 * Created by mmustapha on 11/2/17.
 */
class ExchangeRatePresenter(private val view: ExchangeRateContract.View, private val cryptoCurrency: String) : ExchangeRateContract.Presenter {

    private var value: MutableList<Double> = mutableListOf()
    private var flag: MutableList<Int> = mutableListOf()
    private var flagMap: HashMap<String, Int> = HashMap()
    private var fullNameMap: HashMap<String, String> = HashMap()
    private var exchangeRate: ArrayList<ExchangeRate>? = ArrayList()

    /**
     * Get Bitcoin exchange rates
     */
    private fun getBTCList() {
        val request = JsonObjectRequest(Request.Method.GET, BTC_URL, null,
                Response.Listener<JSONObject> {
                    this.setExchangeList(BTC,it.toString())
                    view.onSuccess(exchangeRate!!)
                },
                Response.ErrorListener {
                    Log.e("NETWORK ERROR", it.toString())
                    view.onFailure("")
                })
        NetworkHelper(view.context()).getInstance().addToRequestQueue(request);
    }

    /**
     * Get Ethereum exchange rates
     */
    private fun getETHList() {
        val request = JsonObjectRequest(Request.Method.GET, ETH_URL, null,
                Response.Listener<JSONObject> {
                    this.setExchangeList(ETH,it.toString())
                    view.onSuccess(exchangeRate!!)
                },
                Response.ErrorListener {
                    Log.e("NETWORK ERROR", it.toString())
                    view.onFailure("")
                })
        NetworkHelper(view.context()).getInstance().addToRequestQueue(request);
    }

    /**
     * Get the list of Crypto-currency exchange rates
     */
    override fun getExchangeList() {
        when (cryptoCurrency) {
            BTC -> {
                this.getBTCList()
            }
            ETH -> {
                this.getETHList()
            }
        }
    }

    /**
     * Populate an ArrayList with the exchange rates
     */
    private fun setExchangeList(cryptoCurrency: String, jsonString: String) {
        val parsedResponse = JSONParser.parseJSON(jsonString, cryptoCurrency)
        val countryCode = view.context().resources.getStringArray(R.array.currency_name_array) // A string array with the list of the currency codes
        this.setExchangeValue(value, parsedResponse)
        this.setFlagArray(flag)
        this.setFlagMap(flagMap)
        this.setCurrencyFullName(fullNameMap)
        for (position in countryCode.indices) {
            val rate = ExchangeRate()
            rate.currencyCode = countryCode[position]
            rate.currencyFullName = fullNameMap[countryCode[position]].toString()
            rate.exchangeValue = value[position]
            rate.countryFlag = flag[position]
            exchangeRate?.add(rate)
        }
    }

    /**
     * Create an array of the exchange values
     */
    private fun setExchangeValue(exchangeValue: MutableList<Double>, currency: Currency) {
        exchangeValue.add(currency.NGN)
        exchangeValue.add(currency.USD)
        exchangeValue.add(currency.EUR)
        exchangeValue.add(currency.GBP)
        exchangeValue.add(currency.JPY)
        exchangeValue.add(currency.CNY)
        exchangeValue.add(currency.CAD)
        exchangeValue.add(currency.AUD)
        exchangeValue.add(currency.CHF)
        exchangeValue.add(currency.SGD)
        exchangeValue.add(currency.INR)
        exchangeValue.add(currency.MYR)
        exchangeValue.add(currency.KES)
        exchangeValue.add(currency.ZAR)
        exchangeValue.add(currency.GHS)
        exchangeValue.add(currency.RUB)
        exchangeValue.add(currency.MXN)
        exchangeValue.add(currency.QAR)
        exchangeValue.add(currency.EGP)
        exchangeValue.add(currency.ILS)
    }

    private fun setFlagArray(flag: MutableList<Int>){
        flag.add(R.drawable.nigeria)
        flag.add(R.drawable.united_states_of_america)
        flag.add(R.drawable.european_union)
        flag.add(R.drawable.united_kingdom)
        flag.add(R.drawable.japan)
        flag.add(R.drawable.china)
        flag.add(R.drawable.canada)
        flag.add(R.drawable.australia)
        flag.add(R.drawable.switzerland)
        flag.add(R.drawable.singapore)
        flag.add(R.drawable.india)
        flag.add(R.drawable.malaysia)
        flag.add(R.drawable.kenya)
        flag.add(R.drawable.south_africa)
        flag.add(R.drawable.ghana)
        flag.add(R.drawable.russia)
        flag.add(R.drawable.mexico)
        flag.add(R.drawable.qatar)
        flag.add(R.drawable.egypt)
        flag.add(R.drawable.israel)
    }

    private fun setFlagMap(map: HashMap<String, Int>){
        val countryCode = view.context().resources.getStringArray(R.array.currency_name_array) // A string array with the list of the currency codes
//        Create a flag for each country currency
        for (i in countryCode.indices){
            map.put(countryCode[i], flag[i])
        }
    }

    private fun setCurrencyFullName(map: HashMap<String, String>){
        val countryCode = view.context().resources.getStringArray(R.array.currency_name_array) // A string array with the list of the currency codes
        val currencyFullName = view.context().resources.getStringArray(R.array.currency_fullname_array) // A string array with the list of the currency ful names
//        Create full name for each country currency
        for (i in countryCode.indices){
            map.put(countryCode[i], currencyFullName[i])
        }
    }


    companion object {
        val BTC_URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC&tsyms=NGN,USD,EUR,GBP," +
                "JPY,CNY,CAD,AUD,CHF,SGD,INR,MYR,KES,ZAR,GHS,RUB,MXN,QAR,EGP,ILS"

        val ETH_URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH&tsyms=NGN,USD,EUR,GBP," +
                "JPY,CNY,CAD,AUD,CHF,SGD,INR,MYR,KES,ZAR,GHS,RUB,MXN,QAR,EGP,ILS"
    }
}