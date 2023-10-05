package com.cedricbahirwe.dialer.model

import android.content.res.Resources

sealed class DialingError(override val message: String) : Throwable() {
    private companion object {
        val context: Resources = Resources.getSystem()
    }

    class CanNotDial : DialingError("Can not dial this code")
    class UnknownFormat(format: String) :
        DialingError("Can not decode this format: $format")

    class EmptyPin : DialingError("Pin Code not found, configure pin and try again")
}