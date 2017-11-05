package com.cryptocheck.util

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Created by mmustapha on 11/2/17.
 */
class NetworkHelper(private val context: Context) {
    private var mInstance: NetworkHelper? = null
    private var requestQueue: RequestQueue? = null

    @Synchronized
    fun getInstance(): NetworkHelper {
        if (mInstance == null) {
            mInstance = NetworkHelper(context)
        }
        return mInstance!!
    }

    private fun getRequestQueue(): RequestQueue? {
        if (requestQueue == null) {
            if (context.applicationContext != null) {
                requestQueue = Volley.newRequestQueue(context.applicationContext)
            }
        }
        return requestQueue
    }

    fun <T> addToRequestQueue(request: Request<T>): RequestQueue? {
        getRequestQueue()!!.add(request)
        return null
    }
}