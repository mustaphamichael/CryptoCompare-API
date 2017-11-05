package com.cryptocheck.view.exchange_rate.adapter;

import android.content.Context
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView
import com.cryptocheck.R;
import com.cryptocheck.model.ExchangeRate;
import com.cryptocheck.view.main.ToggleFragments


class ExchangeRateAdapter(private val context: Context, private val exchangeRate: ArrayList<ExchangeRate>):
        RecyclerView.Adapter<ExchangeRateAdapter.Companion.ViewHolder>() {


    override fun getItemCount(): Int {
        return exchangeRate.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.exchange_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val exchangeRate = exchangeRate[position]
        holder.currencyCode?.text = exchangeRate.currencyCode
        holder.currencyFullName?.text = exchangeRate.currencyFullName
        holder.exchangeRate?.text = exchangeRate.exchangeValue.toString()
        holder.countryFlag?.setImageResource(exchangeRate.countryFlag)
        holder.itemView.setOnClickListener {
            (context as ToggleFragments).
                    toConversionFragment("",exchangeRate)
        }
    }

    companion object {

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var countryFlag: ImageView? = null
            var currencyCode: TextView? = null
            var currencyFullName: TextView? = null
            var exchangeRate: TextView? = null

            init {
                initializeView(itemView)
            }

            private fun initializeView(view: View){
                countryFlag = view.findViewById(R.id.country_flag)
                currencyCode = view.findViewById(R.id.currency_code)
                currencyFullName = view.findViewById(R.id.currency_full_name)
                exchangeRate = view.findViewById(R.id.exchange_rate)
            }

        }
    }

}
