package com.cryptocheck.util

import android.view.View

/**
 * Created by mmustapha on 11/5/17.
 */
object ViewUtil {

    fun toggleView(view: View){
        if (view.visibility == View.VISIBLE){
            view.visibility = View.GONE
        }
        else if (view.visibility == View.GONE){
            view.visibility = View.VISIBLE
        }
    }
}