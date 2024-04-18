package com.cedricbahirwe.dialer.data

import java.util.Date

data class PurchaseDetailModel(val amount: Int = 0, val purchaseDate: Date = Date()) {
    private companion object {
        const val PREFIX_CODE = "*182*2*1*1*1*"
    }

    // `27/03/2023`: MTN disabled the ability to dial airtime USSD that includes Momo PIN for an amount greater than 99.
    // You can dial the code with PIN for amount in the range of 10 to 99
    fun getFullUSSDCode(): String = "$PREFIX_CODE$amount#"
}