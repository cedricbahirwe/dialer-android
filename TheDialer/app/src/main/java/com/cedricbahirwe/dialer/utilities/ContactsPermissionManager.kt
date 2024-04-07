package com.cedricbahirwe.dialer.utilities

import android.content.Context
import android.provider.ContactsContract
import com.cedricbahirwe.dialer.data.Contact
import com.cedricbahirwe.dialer.data.ContactManager

object ContactsPermissionManager {

//    fun hasPermission(): Boolean {
//        return (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
//                == PackageManager.PERMISSION_GRANTED)
//    }

//    private val contactPermissionRequestCode = 101

//    fun requestPermission() {
//        if (hasPermission()) return
//
//        ActivityCompat.requestPermissions(
//            context as Activity,
//            arrayOf(Manifest.permission.READ_CONTACTS),
//            contactPermissionRequestCode
//        )
//        println("Finish here")
//    }

    fun getContacts(context: Context): List<Contact> {
//        if (hasPermission().not()) return emptyList()

        val contactsList = mutableListOf<Contact>()

        // Permission is granted, query the contacts
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val phoneIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val name = it.getString(nameIndex)
                var phoneNumber = it.getString(phoneIndex)

                phoneNumber = phoneNumber.replace("-", "")

                // Add contact to the list
                val existingContact = contactsList.find { contact -> contact.names == name }
                if (existingContact != null) {
                    existingContact.addPhoneNumber(phoneNumber)
                } else {
                    contactsList.add(Contact(name, mutableListOf(phoneNumber)))
                }
            }
        }

        return ContactManager.filterMtnContacts(contactsList)
    }
}