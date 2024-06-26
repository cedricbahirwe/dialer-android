package com.cedricbahirwe.dialer.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cedricbahirwe.dialer.data.Contact
import com.cedricbahirwe.dialer.data.DialerQuickCode
import com.cedricbahirwe.dialer.data.Transaction
import com.cedricbahirwe.dialer.data.TransactionType
import com.cedricbahirwe.dialer.service.AnalyticsTracker
import com.cedricbahirwe.dialer.service.MixPanelTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class TransferViewModel(
    context: Context
): ViewModel() {
    private val _uiState = MutableStateFlow(Transaction("", "", TransactionType.MERCHANT))
    val uiState: StateFlow<Transaction> = _uiState.asStateFlow()

    private val _uiContacts = MutableStateFlow(emptyList<Contact>())
    val selectedContact = MutableStateFlow(Contact.empty)

    private val phoneDialer by lazy {
        PhoneDialer.getInstance(context)
    }

    private val tracker: AnalyticsTracker by lazy {
        MixPanelTracker.getInstance(context)
    }

    fun switchTransactionType() {
        _uiState.update { currentState ->

            currentState.copy(
                type = if (currentState.type == TransactionType.MERCHANT) TransactionType.CLIENT else TransactionType.MERCHANT
            )
        }
    }

    fun handleTransactionAmountChange(value: String) {
        val cleanAmount = value.filter { it.isDigit() }
        _uiState.update {
            it.copy(
                amount = cleanAmount
            )
        }
    }

    fun cleanPhoneNumber(contact: Contact) {
        selectedContact.value = contact
        _uiState.value.number = selectedContact.value.phoneNumbers.first()
    }

    fun clearState() {
        selectedContact.value = Contact.empty
        _uiState.value = Transaction("", "", TransactionType.MERCHANT)
    }

    fun getContacts(): List<Contact> = _uiContacts.value

    fun setContacts(contacts: List<Contact>) {
        _uiContacts.value = contacts
    }

    fun handleTransactionNumberChange(value: String) {
        if(value != _uiState.value.number) {
            val filteredValue = value.filter { it.isDigit() }

            if (_uiState.value.type == TransactionType.MERCHANT) {
                _uiState.update {
                    it.copy(
                        number = filteredValue.take(6)
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        number = filteredValue
                    )
                }
                val matchedContacts = _uiContacts.value.filter { contact ->
                    contact.phoneNumberList.any { it.equals(filteredValue, ignoreCase = true) }
                }

                selectedContact.value = if (matchedContacts.isEmpty()) {
                    Contact("", mutableListOf())
                } else {
                    matchedContacts.first()
                }
            }
        }
    }

    fun transferMoney() {
        if (!_uiState.value.isValid) return
//        println("Transaction triggered ${_uiState.value.fullCode}")
        performQuickDial(DialerQuickCode.Other(_uiState.value.fullCode))
        tracker.logTransaction(_uiState.value)
    }

    private fun performQuickDial(quickCode: DialerQuickCode) {
        phoneDialer.dial(quickCode.ussd)
    }
}

class TransferViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransferViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransferViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}