package com.cedricbahirwe.dialer.model

// https://www.gsmarena.com/glossary.php3?term=pin-code
data class CodePin(var a: Int, var b: Int, var c: Int, var d: Int, var e: Int) {

    private val asString: String
        get() {
            return "$a$b$c$d$e"
        }

    val asDigits: Int
        get() {
            return asString.toIntOrNull() ?: 0
        }

    constructor(value: String) : this(0, 0, 0, 0, 0) {
        if (!value.all { it.isDigit() }) {
            throw ValidationError.InvalidCharacters()
        }

        if (value.length != 5) {
            throw ValidationError.InvalidCount(value.length)
        }

        val digits = value.toCharArray()

        val code = this.getDigits(digits)
        this.a = code.a
        this.b = code.b
        this.c = code.c
        this.d = code.d
        this.e = code.e
    }

    constructor(value: Int) : this(0, 0, 0, 0, 0) {

        if (value.toString().length != 5) {
            throw ValidationError.InvalidCount(value.toString().length)
        }

        val digits = value.toString().toCharArray()
        val code = this.getDigits(digits)
        this.a = code.a
        this.b = code.b
        this.c = code.c
        this.d = code.d
        this.e = code.e
    }

    private fun getDigits(digits: CharArray): CodePin {
        val a = digits[0].code - '0'.code
        val b = digits[1].code - '0'.code
        val c = digits[2].code - '0'.code
        val d = digits[3].code - '0'.code
        val e = digits[4].code - '0'.code
        return CodePin(a, b, c, d, e)
    }
}

private sealed class ValidationError(override val message: String) : Throwable() {
    class InvalidCharacters : ValidationError("The code pin must be composed of digits.")
    class InvalidCount(count: Int) :
        ValidationError("The code pin must be 5 digits, Found $count digits.")
}