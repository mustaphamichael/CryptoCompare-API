package com.cryptocheck.view.main


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cryptocheck.R
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment.Companion.BTC
import com.cryptocheck.view.exchange_rate.ExchangeRateFragment.Companion.ETH
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private lateinit var callback: ToggleFragments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).setActionBarTitle(getString(R.string.app_name))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        view.bitcoin_exchange_btn.setOnClickListener {
            callback.toExchangeRateFragment(BTC)
        }
        view.ethereum_exchange_btn.setOnClickListener {
            callback.toExchangeRateFragment(ETH)
        }
        return view
    }

    /**
     * Method to ensure that the parent activity overrides the Interface
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ToggleFragments) {
            callback = context
        } else {
            throw RuntimeException(context.toString() + " must implement ToggleFragments Interface")
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle(getString(R.string.app_name))
    }

    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
