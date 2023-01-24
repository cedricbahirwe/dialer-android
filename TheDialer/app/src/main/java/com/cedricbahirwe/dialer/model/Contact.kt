package com.cedricbahirwe.dialer.model

data class Contact(val names: String, private var phoneNumbers: List<String>) {

    private val id: String
        get() {
            return names + phoneNumbers.toString()
        }

    fun updatePhones(numbers: List<String>) {
        phoneNumbers = numbers
    }
}