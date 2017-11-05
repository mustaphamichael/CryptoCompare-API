package com.cryptocheck.contract

import android.content.Context
import com.cryptocheck.BaseView
import com.cryptocheck.model.ExchangeRate

/**
 * Created by mmustapha on 11/5/17.
 */
class ExchangeRateContract {
    interface View: BaseView<Presenter> {
        fun context():Context
        fun onSuccess(exchangeRate: ArrayList<ExchangeRate>)
        fun onFailure(errorMessage: String)
    }

    interface Presenter {
        fun getExchangeList()
    }
}