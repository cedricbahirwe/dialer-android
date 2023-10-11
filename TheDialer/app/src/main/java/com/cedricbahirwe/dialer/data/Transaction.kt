package com.cedricbahirwe.dialer.data

import com.cedricbahirwe.dialer.data.protocol.Identifiable
import java.util.*


data class Transaction(
    var amount: String,
    var number: String,
    var type: TransactionType
) : Identifiable<String> {
    override val id: String = Date().toString()
    private val trailingCode: String
        get() = number.replace(" ", "") + "*" + amount

    private val doubleAmount: Double
        get() = amount.toDoubleOrNull() ?: 0.0

    val estimatedFee: Int
        get() {
            return when (type) {
                TransactionType.CLIENT -> {
                    when (doubleAmount.toInt()) {
                        in 0..1000 -> 20
                        in 1001..10000 -> 100
                        in 10001..150000 -> 250
                        in 150001..2000000 -> 1500
                        else -> -1
                    }
                }

                TransactionType.MERCHANT -> 0
            }
        }

    val fullCode: String
        get() = "*182*1*1*$trailingCode#"

    val isValid: Boolean
        get() = when (type) {
            TransactionType.CLIENT -> doubleAmount > 0.0 && number.length >= 8
            TransactionType.MERCHANT -> doubleAmount > 0.0 && number.length in 5..6
        }
}
val Transaction.isMerchantTransfer: Boolean
    get() = type == TransactionType.MERCHANT
