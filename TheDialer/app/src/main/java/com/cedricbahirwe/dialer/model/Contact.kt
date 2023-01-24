package com.cedricbahirwe.dialer.model

data class Contact(val names: String, private var phoneNumbers: List<String>) {

    private val id: String
    get() { return names + phoneNumbers.toString() }

    fun updatePhones(numbers: List<String>) {
        phoneNumbers = numbers
    }
    companion object {
        val example = Contact("Kate Bell", listOf("(555) 564-8583", "(415) 555-3695"))
        val example1 = Contact("John Smith", listOf("(415) 555-3695"))
    }
}