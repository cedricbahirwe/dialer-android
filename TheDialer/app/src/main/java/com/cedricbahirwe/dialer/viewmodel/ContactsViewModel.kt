package com.cedricbahirwe.dialer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cedricbahirwe.dialer.data.Contact
import com.cedricbahirwe.dialer.data.ContactsDictionary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class ContactsViewModel(contacts: List<Contact>) : ViewModel() {
    lateinit var completion: (Contact) -> Unit

    private val _contactsDict =
        MutableStateFlow(ContactsDictionary.transform(contacts)) //PreviewContent.generateDummyContactsDictionary())
    val hasContacts: Flow<Boolean> get() = _contactsDict.map { it.isNotEmpty() }

    private val _selectedContact = MutableStateFlow(Contact.empty)
    val selectedContact: StateFlow<Contact> get() = _selectedContact

    var showPhoneNumberSelector = MutableStateFlow(false)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    // Define searchedContacts as a StateFlow
    val searchedContacts: Flow<List<ContactsDictionary>> = combine(
        _contactsDict,
        _searchQuery
    ) { contactsDict, searchQuery ->
        val sortedContacts = contactsDict.flatMap { it.contacts }.sortedBy { it.names }
        if (searchQuery.isEmpty()) {
            contactsDict
        } else {
            val filteredContacts = sortedContacts.filter { contact ->
                contact.names.contains(searchQuery, ignoreCase = true) ||
                        contact.phoneNumbers.joinToString("").contains(searchQuery)
            }
            ContactsDictionary.transform(filteredContacts)
        }
    }.distinctUntilChanged()


    init {
        println("Init here ${contacts.size}", )
    }
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

    fun hidePhoneNumberSelector() {
        showPhoneNumberSelector.value = false
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
    }

//    fun getAllContacts(): List<Contact> = _contactsDict.value.getAllContactsSorted()
}

class ContactsViewModelFactory(
    private val contacts: List<Contact>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(contacts) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



//private fun fetchContacts() {
//    if (_contactsDict.value.isNotEmpty()) return
//    println("Reaching here")
//
//    val contactPermissionRequestCode = 101
//    val contactsList = mutableListOf<Contact>()
//
//    // Check if the permission to read contacts is granted
//    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
//        != PackageManager.PERMISSION_GRANTED
//    ) {
//        // Request the permission
//        ActivityCompat.requestPermissions(
//            context as Activity,
//            arrayOf(Manifest.permission.READ_CONTACTS),
//            contactPermissionRequestCode
//        )
//    } else {
//        // Permission is granted, query the contacts
//        val cursor = context.contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            null,
//            null,
//            null,
//            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
//        )
//
//
//        cursor?.use {
//            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
//            val phoneIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
//
//            while (it.moveToNext()) {
//                val name = it.getString(nameIndex)
//                var phoneNumber = it.getString(phoneIndex)
//
//                phoneNumber = phoneNumber.replace("-", "")
//
//                // Add contact to the list
//                val existingContact = contactsList.find { contact -> contact.names == name }
//                if (existingContact != null) {
//                    existingContact.addPhoneNumber(phoneNumber)
//                } else {
//                    contactsList.add(Contact(name, mutableListOf(phoneNumber)))
//                }
//            }
//        }
//    }
//
//    val mtnContacts = ContactManager.filterMtnContacts(contactsList)
//    _contactsDict.value = ContactsDictionary.transform(mtnContacts)
//}
