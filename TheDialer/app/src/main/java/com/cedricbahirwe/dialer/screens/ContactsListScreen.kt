package com.cedricbahirwe.dialer.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cedricbahirwe.dialer.data.Contact
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.ContactsViewModel


@Composable
fun ContactsList(
    viewModel: ContactsViewModel = viewModel(),
    onSelectContact: (Contact) -> Unit
) {
    val showPhoneNumberSelector by viewModel.showPhoneNumberSelector.collectAsState()
    val selectedContact by viewModel.selectedContact.collectAsState()

    val searchedContacts by viewModel.searchedContacts.collectAsState(initial = emptyList())
    val hasContacts by viewModel.hasContacts.collectAsState(initial = false)

    val searchQuery by viewModel.searchQuery.collectAsState()

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
                            backgroundColor = Color.Transparent,
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

    if (hasContacts.not()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(16.dp),

        ) {
            Text("No Contacts Found.",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text("Please make sure the app has permission to access your contacts.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    } else {

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {
            SearchField(
                searchQuery = searchQuery,
                onSearch = {
                   viewModel.onSearch(it)
                           },
                onEndEditing = {
                    viewModel.onSearch("")
                }
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                searchedContacts.forEach { section ->
                    item {
                        Text(
                            text = section.letter.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray
                        )
                    }

                    items(section.contacts) { contact ->
                        val isFirstContact = contact == section.contacts.first()
                        val isLastContact = contact == section.contacts.last()

                        val topRadius = if (isFirstContact) 16.dp else 0.dp
                        val bottomRadius = if (isLastContact) 16.dp else 0.dp

                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topRadius, topRadius, bottomRadius, bottomRadius
                                    )
                                )
                                .background(MaterialTheme.colors.surface)
                        ) {
                            ContactRowView(contact, onClick = {
                                viewModel.completion = onSelectContact
                                viewModel.handleSelection(contact)
                            })

                            if (contact != section.contacts.last()) {
                                Divider(Modifier.padding(start = 16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ContactsListPreview() {
    DialerTheme(darkTheme = false) {
        ContactsList(
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
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = contact.names,
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colors.primary,
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
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}