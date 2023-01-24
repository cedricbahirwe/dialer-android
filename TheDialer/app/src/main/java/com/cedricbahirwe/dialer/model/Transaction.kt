package com.cedricbahirwe.dialer.model

import com.cedricbahirwe.dialer.model.protocol.Identifiable
import java.util.*

data class Transaction(
    var amount: String,
    var number: String,
    var type: TransactionType = TransactionType.CLIENT
) : Identifiable<String> {
    override val id: String = Date().toString()
    private val trailingCode: String
        get() = number.replace(" ", "") + "*" + amount

    private val doubleAmount: Double
        get() = amount.toDoubleOrNull() ?: 0.0

    val estimatedFee: Int
        get() {
            if (type == TransactionType.CLIENT) {
                for ((key, value) in transactionFees) {
                    if (key.contains(doubleAmount.toInt())) {
                        return value
                    }
                }
                return -1
            } else {
                return 0
            }
        }

    val fullCode: String
        get() = "*182*1*1*$trailingCode#"

    val isValid: Boolean
        get() = when (type) {
            TransactionType.CLIENT -> doubleAmount > 0.0 && number.length >= 8
            TransactionType.MERCHANT -> doubleAmount > 0.0 && number.length in 5..6
        }

    enum class TransactionType {
        CLIENT, MERCHANT
    }

    private companion object {
        val transactionFees =
            mapOf(0..1000 to 20, 1001..10000 to 100, 10001..150000 to 250, 150001..2000000 to 1500)
    }
}

