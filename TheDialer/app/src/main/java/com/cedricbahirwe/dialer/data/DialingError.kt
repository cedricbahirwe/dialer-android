package com.cedricbahirwe.dialer.data

sealed class DialingError(override val message: String) : Throwable() {
    class CanNotDial : DialingError("Can not dial this code")
}