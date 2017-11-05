package com.cryptocheck.view.conversion

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.cryptocheck.R
import com.cryptocheck.model.ExchangeRate
import com.cryptocheck.util.NumberUtils
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment.Companion.BTC
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment.Companion.ETH
import com.cryptocheck.view.main.MainActivity
import kotlinx.android.synthetic.main.conversion_form.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class ConversionFragment : Fragment() {

    private lateinit var cryptoCurrency: String
    private lateinit var currencyCode: String
    private var exchangeValue: Double? = null
    private lateinit var exchangeRate: ExchangeRate
    private var conversionOutput: TextView?= null
    private var currencyFormat: DecimalFormat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            exchangeRate = arguments.getParcelable(EXCHANGE_RATE)
            cryptoCurrency = arguments.getString(CRYPTO_CURRENCY)
            currencyCode = exchangeRate.currencyCode
            exchangeValue = exchangeRate.exchangeValue
        }
        (activity as MainActivity).setActionBarTitle("$cryptoCurrency - $currencyCode")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_conversion, container, false)
        conversionOutput = view.exchange_amount
        onInputting(view.currency_amount)
        view.currency_code.text = currencyCode
        when(cryptoCurrency){
            BTC-> {
                view.cryptocurrency_image.setImageResource(R.drawable.bitcoin_icon)
            }
            ETH-> {
                view.cryptocurrency_image.setImageResource(R.drawable.ethereum_icon)
            }
        }
        view.country_flag.setImageResource(exchangeRate.countryFlag)
        view.currency_full_name.text = exchangeRate.currencyFullName
        currencyFormat = NumberFormat.getCurrencyInstance(Locale("en", "NG")) as DecimalFormat
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle("$cryptoCurrency - $currencyCode")
    }

    /**
     * Handles edit text input
     */
    private fun onInputting(input: EditText){
        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                if (text!!.isNotEmpty()) {
                    val conversion = (text.toString().toDouble() * exchangeValue!!)
                    val output = NumberUtils.toCurrency(currencyFormat!!, conversion)
                    conversionOutput?.text = output
                }
                else if (text.isEmpty()) conversionOutput?.text = "0.0"
            }
        })
    }

    companion object {
        val CRYPTO_CURRENCY="CRYPTO_CURRENCY"
        val EXCHANGE_RATE="EXCHANGE_VALUE"

        fun newInstance(cryptoCurrency: String, exchangeRate: ExchangeRate): ConversionFragment {
            val fragment = ConversionFragment()
            val args = Bundle()
            args.putString(CRYPTO_CURRENCY, cryptoCurrency)
            args.putParcelable(EXCHANGE_RATE, exchangeRate)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor