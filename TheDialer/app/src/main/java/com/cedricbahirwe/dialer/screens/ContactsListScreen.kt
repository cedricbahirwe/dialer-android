package com.cedricbahirwe.dialer.screens


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cedricbahirwe.dialer.data.Contact
import com.cedricbahirwe.dialer.data.ContactManager
import com.cedricbahirwe.dialer.data.ContactsDictionary
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun ContactList(
    viewModel: ContactsViewModel = viewModel(),
    onSelectContact: (Contact) -> Unit
) {
    val contacts: List<Contact> by viewModel.contacts.collectAsState()
    val showPhoneNumberSelector by viewModel.showPhoneNumberSelector.collectAsState()
    val selectedContact by viewModel.selectedContact.collectAsState()


    if (showPhoneNumberSelector) {
        AlertDialog(
            onDismissRequest = {
                viewModel.hidePhoneNumberSelector()
            },
            title = {
                Text("Phone Number.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                    },
            text = {
                Text(
                    text = "Select a phone number to send to",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.fillMaxWidth()
                )
                   },
            buttons = {
                selectedContact.phoneNumbers.forEach { phoneNumber ->
                    Divider()
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(),
                        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                        contentPadding = PaddingValues(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Unspecified,
                            contentColor = AccentBlue
                        ),
                        onClick = {
                            viewModel.completion = onSelectContact
                            viewModel.hidePhoneNumberSelector()
                            viewModel.managePhoneNumber(phoneNumber)
                        }
                    ) {
                        Text(phoneNumber)
                    }
                }
            },
        )
    }

    LazyColumn {
        items(contacts) { contact ->
            ContactRowView(contact, onClick = {
                viewModel.completion = onSelectContact
                viewModel.handleSelection(contact)
            })
            Divider()
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ContactsListPreview() {
    DialerTheme {
        ContactList(
            viewModel = ContactsViewModel(LocalContext.current),
            onSelectContact = { }
        )
    }
}

@Composable
fun ContactRowView(contact: Contact, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = contact.names,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Medium
            ),
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            if (contact.phoneNumbers.size == 1) {
                Text(
                    text = contact.phoneNumbers[0],
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            } else {
                Text(
                    text = "${contact.phoneNumbers[0]}, +${contact.phoneNumbers.size - 1}more",
                    style = MaterialTheme.typography.overline,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}

class ContactsViewModel(val context: Context) : ViewModel() {
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> get() = _contacts

    private val _selectedContact = MutableStateFlow(Contact.empty)
    val selectedContact: StateFlow<Contact> get() = _selectedContact

    var showPhoneNumberSelector = MutableStateFlow(false)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val contactsDict: List<ContactsDictionary> get() = ContactsDictionary.transform(_contacts.value)

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
        fetchContacts()
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
        val CONTACT_PERMISSION_REQUEST_CODE = 101
        val contactsList = mutableListOf<Contact>()

        // Check if the permission to read contacts is granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                CONTACT_PERMISSION_REQUEST_CODE
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

fun String.isMtnNumber(): Boolean {
    val number = this.trim().replace(" ", "")
    return number.startsWith("+25078") || number.startsWith("25078") || number.startsWith("078") ||
            number.startsWith("+25079") || number.startsWith("25079") || number.startsWith("079")
}

fun String.asMtnNumber(): String {
    var mtnNumber = this
    if (mtnNumber.startsWith("25")) {
        mtnNumber = mtnNumber.removeRange(0, 2)
    } else if (mtnNumber.startsWith("+25")) {
        mtnNumber = mtnNumber.removeRange(0, 3)
    }
    return mtnNumber
}