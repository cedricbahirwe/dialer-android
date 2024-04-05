package com.cedricbahirwe.dialer.data

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

private fun String.isMtnNumber(): Boolean {
    val number = this.trim().replace(" ", "")
    return number.startsWith("+25078") || number.startsWith("25078") || number.startsWith("078") ||
            number.startsWith("+25079") || number.startsWith("25079") || number.startsWith("079")
}

private fun String.asMtnNumber(): String {
    var mtnNumber = this
    if (mtnNumber.startsWith("25")) {
        mtnNumber = mtnNumber.removeRange(0, 2)
    } else if (mtnNumber.startsWith("+25")) {
        mtnNumber = mtnNumber.removeRange(0, 3)
    }
    return mtnNumber
}