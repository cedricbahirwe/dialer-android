package com.cedricbahirwe.dialer.model

import java.util.Date

data class PurchaseDetailModel(val amount: Int = 0, val purchaseDate: Date = Date()) {
    val fullCode: String
        get() { return "${prefixCode}$amount*PIN#" }

    fun getDialCode(pin: String): String {
        return if (pin.isEmpty()) {
            "${prefixCode}$amount#"
        } else {
            "*${prefixCode}$amount*$pin#"
        }
    }

    private companion object {
        const val prefixCode = "*18*2*2*1*1*1*"
    }
}