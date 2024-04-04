package com.cedricbahirwe.dialer.data

import com.cedricbahirwe.dialer.screens.asMtnNumber
import com.cedricbahirwe.dialer.screens.isMtnNumber

object ContactManager {
    fun filterMtnContacts(contacts: List<Contact>): List<Contact> {
        val resultingContacts: MutableList<Contact> = mutableListOf()

        for (contact in contacts) {
            if (contact.phoneNumbers.isNotEmpty()) {
                val contactPhoneNumbers = contact.phoneNumbers
                val mtnNumbersFormat = contactPhoneNumbers.filter { it.isMtnNumber() }

                val pureMtnNumbers = mtnNumbersFormat.mapNotNull { (it as? String)?.asMtnNumber() }
                if (pureMtnNumbers.isNotEmpty()) {
                    val newContact = Contact(names = contact.names,
                        phoneNumbers = pureMtnNumbers.toMutableList())
                    resultingContacts.add(newContact)
                }
            }
        }

        return resultingContacts
    }
}