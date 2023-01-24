package com.cedricbahirwe.dialer.model

import java.io.Serializable

data class ElectricityMeter(val number: String) : Serializable {
    val id: String = number

    init {
        val cleanValue = cleanNumber(number)
        require(cleanValue.isNotEmpty()) { throw MeterNumberError.EmptyMeterNumber() }
        require(cleanValue.all { it.isDigit() }) { throw MeterNumberError.InvalidMeterNumber() }
    }

    private sealed class MeterNumberError(override val message: String): Throwable() {
        class EmptyMeterNumber: MeterNumberError("Meter can not be empty.")
        class InvalidMeterNumber: MeterNumberError("Meter Number should only contains digits between 0-9.")
    }

    private fun cleanNumber(number: String) : String {
        var cleanNumber = number.replace(" ", "")
        cleanNumber = cleanNumber.trim()
        return cleanNumber
    }
}
