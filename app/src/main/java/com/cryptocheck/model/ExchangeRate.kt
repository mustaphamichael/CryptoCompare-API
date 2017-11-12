package com.cryptocheck.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by mmustapha on 10/30/17.
 */

data class ExchangeRate(
        var currencyCode: String = "",
        var currencyFullName: String = "",
        var exchangeValue: Double = 0.0,
        var countryFlag: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(currencyCode)
        parcel.writeString(currencyFullName)
        parcel.writeDouble(exchangeValue)
        parcel.writeInt(countryFlag)
    }

    override fun describeContents(): Int {
        return 0
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExchangeRate

        if (other.currencyCode != this.currencyCode) return false

        return true
    }

    companion object CREATOR : Parcelable.Creator<ExchangeRate> {
        override fun createFromParcel(parcel: Parcel): ExchangeRate {
            return ExchangeRate(parcel)
        }

        override fun newArray(size: Int): Array<ExchangeRate?> {
            return arrayOfNulls(size)
        }
    }
}