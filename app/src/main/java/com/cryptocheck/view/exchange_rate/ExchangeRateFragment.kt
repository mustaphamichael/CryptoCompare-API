package com.cryptocheck.view.exchange_rate

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.cryptocheck.R
import com.cryptocheck.contract.ExchangeRateContract
import com.cryptocheck.model.ExchangeRate
import com.cryptocheck.presenter.ExchangeRatePresenter
import com.cryptocheck.view.exchange_rate.adapter.ExchangeRateAdapter
import com.cryptocheck.view.main.MainActivity
import com.cryptocheck.view.main.ToggleFragments
import kotlinx.android.synthetic.main.fragment_exchange.view.*


class ExchangeRateFragment : Fragment(), ExchangeRateContract.View {

    private lateinit var callback: ToggleFragments

    private lateinit var cryptoCurrency: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyMsg: LinearLayout
    private lateinit var refreshButton: Button
    private var exchangeRateAdapter: ExchangeRateAdapter? = null
    private var exchangeRates: ArrayList<ExchangeRate> = ArrayList()

    private lateinit var presenter: ExchangeRateContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            cryptoCurrency = arguments.getString(CRYPTO_CURRENCY)
        }
        (activity as MainActivity).setActionBarTitle(cryptoCurrency)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_exchange, container, false)
        recyclerView = view.recycler_view
        progressBar = view.progress_bar
        emptyMsg = view.empty_data_message
        refreshButton = view.refresh_btn

        recyclerView.layoutManager = LinearLayoutManager(context)
        exchangeRateAdapter = ExchangeRateAdapter(context, exchangeRates)
        recyclerView.adapter = exchangeRateAdapter

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = ExchangeRatePresenter(this, cryptoCurrency)
        this.setPresenter(presenter)
        presenter.getExchangeList()
        progressBar.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setActionBarTitle(cryptoCurrency)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_exchange_rate, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_refresh -> {
                presenter.getExchangeList()
                progressBar.visibility = View.VISIBLE
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Method to ensure that the parent activity overrides the Interface
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ToggleFragments) {
            callback = context
        } else {
            throw RuntimeException(context.toString() + " must implement ToggleFragments Interface") as Throwable
        }
    }

    override fun onPause() {
        super.onPause()
        progressBar.visibility = View.GONE
    }


    /**
     * Display view for empty server response
     */
    private fun emptyMessageListener(){
        progressBar.visibility = View.GONE
        emptyMsg.visibility = View.VISIBLE
        setHasOptionsMenu(false)

        refreshButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            emptyMsg.visibility = View.GONE
            setHasOptionsMenu(true)
            presenter.getExchangeList()
        }
    }

    override fun setPresenter(presenter: ExchangeRateContract.Presenter) {
        this.presenter = presenter
    }

    override fun context(): Context {
        return context
    }

    override fun onSuccess(exchangeRate: ArrayList<ExchangeRate>) {
        if (exchangeRate.isNotEmpty()){
            exchangeRate.forEach { if (!this.exchangeRates.contains(it)) this.exchangeRates.add(it) }
            progressBar.visibility = View.GONE
            setHasOptionsMenu(true)
            exchangeRateAdapter = ExchangeRateAdapter(context, exchangeRates)
            recyclerView.adapter = exchangeRateAdapter
            exchangeRateAdapter?.notifyDataSetChanged()
        }
    }

    override fun onFailure(errorMessage: String) {
        if (view != null){
            if (this.exchangeRates.isEmpty()) emptyMessageListener()
            Snackbar.make(view!!, getString(R.string.network_error_message), Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        val BTC = "BTC"
        val ETH = "ETH"
        val CRYPTO_CURRENCY = "CRYPTO_CURRENCY"

        fun newInstance(cryptoCurrency: String): ExchangeRateFragment {
            val fragment = ExchangeRateFragment()
            val args = Bundle()
            args.putString(CRYPTO_CURRENCY, cryptoCurrency)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor