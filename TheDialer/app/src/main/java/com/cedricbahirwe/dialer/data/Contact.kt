package com.cedricbahirwe.dialer.data

data class Contact(val names: String, private var phoneNumbers: List<String>) {

    private val id: String
        get() {
            return names + phoneNumbers.toString()
        }

    fun updatePhones(numbers: List<String>) {
        phoneNumbers = numbers
    }

    val phoneNumberList: List<String> get() = phoneNumbers
}