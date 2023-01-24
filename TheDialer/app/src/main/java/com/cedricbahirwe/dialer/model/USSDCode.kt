package com.cedricbahirwe.dialer.model

import java.util.*

data class USSDCode(
    override val id: UUID = UUID.randomUUID(),
    val title: String,
    var ussd: String
) : Identifiable<UUID> {

    companion object {
        private const val STAR_SYMBOL = '*'
        private const val HASH_SYMBOL = '#'

        fun equals(lhs: USSDCode, rhs: USSDCode): Boolean {
            return lhs.ussd == rhs.ussd || lhs.title == rhs.title
        }
    }

    constructor(title: String, ussd: String) : this(UUID.randomUUID(), title, ussd) {
        if (title.isEmpty()) {
            throw USSDCodeValidationError.EmptyTitle()
        }
        this.ussd = validateUSSD(ussd)
    }

    private fun validateUSSD(code: String): String {
        if (code.isEmpty()) {
            throw USSDCodeValidationError.EmptyUSSD()
        }

        if (!code.startsWith(STAR_SYMBOL)) {
            throw USSDCodeValidationError.InvalidFirstCharacter()
        }

        if (code.substring(1).startsWith(STAR_SYMBOL)) {
            throw USSDCodeValidationError.InvalidUSSD()
        }

        if (!code.endsWith(HASH_SYMBOL)) {
            throw USSDCodeValidationError.InvalidLastCharacter()
        }

        if (code.count { it == HASH_SYMBOL } != 1) {
            throw USSDCodeValidationError.InvalidUSSD()
        }

        if (code.any { it !in "0123456789*#" }) {
            throw USSDCodeValidationError.InvalidUSSD()
        }

        return code
    }
}

// MARK: Validation
private sealed class USSDCodeValidationError : Throwable() {
    class EmptyTitle : USSDCodeValidationError()
    class EmptyUSSD : USSDCodeValidationError()
    class InvalidFirstCharacter : USSDCodeValidationError()
    class InvalidLastCharacter : USSDCodeValidationError()
    class InvalidUSSD : USSDCodeValidationError()
}