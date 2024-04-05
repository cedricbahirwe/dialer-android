package com.cedricbahirwe.dialer.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.cedricbahirwe.dialer.data.Contact
import com.cedricbahirwe.dialer.data.ContactManager
import com.cedricbahirwe.dialer.data.ContactsDictionary
import com.cedricbahirwe.dialer.data.PreviewContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

final class ContactsViewModel(private val context: Context) : ViewModel() {
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())

    private val _selectedContact = MutableStateFlow(Contact.empty)
    val selectedContact: StateFlow<Contact> get() = _selectedContact

    var showPhoneNumberSelector = MutableStateFlow(false)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val contactsDict: List<ContactsDictionary> get() = PreviewContent.generateDummyContactsDictionary()//ContactsDictionary.transform(_contacts.value)

    val searchedContacts: List<ContactsDictionary>
        get() {
            val contacts = _contacts.value.sortedBy { it.names }
            return if (_searchQuery.value.isEmpty()) {
                contactsDict
            } else {
                val filteredContacts = contacts.filter { contact ->
                    contact.names.contains(_searchQuery.value, ignoreCase = true) ||
                            contact.phoneNumbers.joinToString("").contains(_searchQuery.value)
                }
                ContactsDictionary.transform(filteredContacts)
            }
        }

    init {
        // Fetch contacts when the ViewModel is initialized
//        fetchContacts()
    }

    lateinit var completion: (Contact) -> Unit

    fun handleSelection(contact: Contact) {
        _selectedContact.value = contact

        if (contact.phoneNumbers.size == 1) {
            completion(_selectedContact.value)
        } else {
            showPhoneNumberSelector.value = true
        }
    }

    fun managePhoneNumber(phone: String) {
        _selectedContact.value.let { selectedContact ->
            val updatedContact = selectedContact.copy(phoneNumbers = mutableListOf(phone))
            _selectedContact.value = updatedContact
        }
        completion(_selectedContact.value)
    }


    private fun fetchContacts() {
        val contactPermissionRequestCode = 101
        val contactsList = mutableListOf<Contact>()

        // Check if the permission to read contacts is granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                contactPermissionRequestCode
            )
        } else {
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
                    val phoneNumber = it.getString(phoneIndex)

                    // Add contact to the list
                    val existingContact = contactsList.find { contact -> contact.names == name }
                    if (existingContact != null) {
                        existingContact.addPhoneNumber(phoneNumber)
                    } else {
                        contactsList.add(Contact(name, mutableListOf(phoneNumber)))
                    }
                }
            }
        }

        _contacts.value = ContactManager.filterMtnContacts(contactsList)
    }

    fun hidePhoneNumberSelector() {
        showPhoneNumberSelector.value = false
    }
}
