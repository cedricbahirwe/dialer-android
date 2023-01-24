package com.cedricbahirwe.dialer.model

sealed class DialerQuickCode {
    class MobileWalletBalance(val code: CodePin?) : DialerQuickCode()
    class Electricity(val meter: String, val amount: Int, val code: CodePin?) : DialerQuickCode()
    class Other(val fullCode: String) : DialerQuickCode()

    private fun codeSuffix(code: CodePin?): String {
        return if (code != null) {
            "*${code.asDigits}#"
        } else {
            "#"
        }
    }

    val ussd: String
        get() = when (this) {
            is MobileWalletBalance -> "*182*6*1${codeSuffix(code)}"
            is Electricity -> "*182*2*2*1*1*$meter*$amount${codeSuffix(code)}"
            is Other -> fullCode
        }
}