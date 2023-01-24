package com.cedricbahirwe.dialer.model

data class PurchaseDetailModel(var amount: Int = 0, var type: CodeType = CodeType.MOMO) {
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

    enum class CodeType(val value: String) {
        MOMO("momo"), CALL("call"), MESSAGE("message"),OTHER("other")
    }
}