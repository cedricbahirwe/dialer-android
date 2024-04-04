package com.cedricbahirwe.dialer.data

import com.cedricbahirwe.dialer.data.protocol.Identifiable

data class Contact(val names: String, val phoneNumbers: MutableList<String>) {

    private val id: String
        get() {
            return names + phoneNumbers.toString()
        }

    fun addPhoneNumber(number: String) {
        phoneNumbers.add(number)
    }

    val phoneNumberList: List<String> get() = phoneNumbers

    companion object {
        val empty = Contact("", mutableListOf())
    }
}
data class ContactsDictionary(val letter: Char, val contacts: List<Contact>): Identifiable<Char> {
    override val id: Char = letter

    companion object {
        fun transform(contacts: List<Contact>): List<ContactsDictionary> {
            val contactsDictionary = contacts.groupBy { contact ->
                contact.names.firstOrNull()?.uppercaseChar() ?: ' '
            }.map { (letter, contacts) ->
                ContactsDictionary(letter = letter, contacts = contacts)
            }

            return contactsDictionary.sortedBy { it.letter }
        }
    }
}
