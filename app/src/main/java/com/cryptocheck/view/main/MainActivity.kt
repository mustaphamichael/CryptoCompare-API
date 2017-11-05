package com.cryptocheck.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import com.cryptocheck.R
import com.cryptocheck.model.ExchangeRate
import com.cryptocheck.view.conversion.ConversionFragment
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment

class MainActivity : AppCompatActivity(), ToggleFragments {

    private var mCryptoCurrency: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (findViewById<FrameLayout>(R.id.fragment_container) != null) {
            if (savedInstanceState != null) return
            supportFragmentManager.beginTransaction().add(R.id.fragment_container,
                    MainFragment.newInstance(), MAIN_FRAGMENT).commit()
        }
    }

    /**
     * Method for setting Action Bar Title
     */
    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun toExchangeRateFragment(cryptoCurrency: String) {
        mCryptoCurrency = cryptoCurrency
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                ExchangeRateFragment.newInstance(cryptoCurrency), EXCHANGE_FRAGMENT).addToBackStack(MAIN_FRAGMENT).commit()
    }

    override fun toConversionFragment(cryptoCurrency: String, exchangeRate: ExchangeRate) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                ConversionFragment.newInstance(mCryptoCurrency!!, exchangeRate), CONVERSION_FRAGMENT).addToBackStack(EXCHANGE_FRAGMENT).commit()
    }

    companion object {
        val MAIN_FRAGMENT = "MAIN_FRAGMENT"
        val EXCHANGE_FRAGMENT = "EXCHANGE_FRAGMENT"
        val CONVERSION_FRAGMENT = "CONVERSION_FRAGMENT"
    }
}
