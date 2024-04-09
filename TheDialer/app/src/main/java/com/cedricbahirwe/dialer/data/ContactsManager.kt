package com.cedricbahirwe.dialer.data

object ContactManager {
    fun filterMtnContacts(contacts: List<Contact>): List<Contact> {
        val resultingContacts: MutableList<Contact> = mutableListOf()

        for (contact in contacts) {
            if (contact.phoneNumbers.isNotEmpty()) {
                val contactPhoneNumbers = contact.phoneNumbers
                val mtnNumbersFormat = contactPhoneNumbers.filter { it.isMtnNumber() }

                val pureMtnNumbers =
                    mtnNumbersFormat.mapNotNull { (it as? String)?.asMtnNumberWithoutCountryCode() }
                if (pureMtnNumbers.isNotEmpty()) {
                    val newContact = Contact(
                        names = contact.names,
                        phoneNumbers = pureMtnNumbers.toMutableList()
                    )
                    resultingContacts.add(newContact)
                }
            }
        }

        return resultingContacts
    }
}

private fun String.isMtnNumber(): Boolean {
    val number = this.trim().replace(" ", "")
    val regex = Regex("^((\\+250)?|0)(78|79)\\d{7}\$")
    return regex.matches(number)
}

private fun String.asMtnNumberWithoutCountryCode(): String {
    var mtnNumber = this
    if (mtnNumber.startsWith("25")) {
        mtnNumber = mtnNumber.removeRange(0, 2)
    } else if (mtnNumber.startsWith("+25")) {
        mtnNumber = mtnNumber.removeRange(0, 3)
    }
    return mtnNumber
}