package com.cedricbahirwe.dialer.data

import com.cedricbahirwe.dialer.data.protocol.Identifiable

typealias Contacts = List<Contact>

data class Contact(val names: String, val phoneNumbers: MutableList<String>) {

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

fun List<ContactsDictionary>.getAllContactsSorted(): List<Contact> {
    return this.flatMap { it.contacts }
}
