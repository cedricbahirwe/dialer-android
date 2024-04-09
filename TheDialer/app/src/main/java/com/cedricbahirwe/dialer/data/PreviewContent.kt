package com.cedricbahirwe.dialer.data

import kotlin.random.Random

sealed class PreviewContent {
    companion object {
        private val examplePurchaseDetail = PurchaseDetailModel(amount = 100)
        val exampleRecentCode = RecentDialCode(detail = examplePurchaseDetail)

        // Function to generate Rwandan phone numbers
        private fun generateRwandanPhoneNumber(): String {
            val random = Random.Default
            val firstDigit = listOf("78", "79").random()
            val countryCode = if (random.nextBoolean()) "+250" else "0"
            val remainingDigits = (1..8).map { random.nextInt(10) }.joinToString("")
            return "$countryCode$firstDigit$remainingDigits"
        }

        // Function to generate dummy data for ContactsDictionary
        fun generateDummyContactsDictionary(): List<ContactsDictionary> {
            val alphabet = ('A'..'Z').toList()
            return alphabet.map { letter ->
                val contacts = (1..5).map {
                    val names = "$letter contact $it"
                    val phoneNumbers = (1..Random.nextInt(1, 4)).map { generateRwandanPhoneNumber() }.toMutableList()
                    Contact(names, phoneNumbers)
                }
                ContactsDictionary(letter, contacts)
            }
        }
    }
}