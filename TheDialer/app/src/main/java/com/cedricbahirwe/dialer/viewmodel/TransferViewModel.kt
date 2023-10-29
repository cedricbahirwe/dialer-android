package com.cedricbahirwe.dialer.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cedricbahirwe.dialer.data.Contact
import com.cedricbahirwe.dialer.data.Transaction
import com.cedricbahirwe.dialer.data.TransactionType
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TransferViewModel(
    context: Context,
    private val settings: AppSettingsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(Transaction("", "", TransactionType.MERCHANT))
    val uiState: StateFlow<Transaction> = _uiState.asStateFlow()
    
    // TODO: - These two will be used when implementing the Contact Picker
    private val _uiContacts = MutableStateFlow(emptyList<Contact>())
    private var selectedContact: Contact? = null

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

    fun handleTransactionNumberChange(value: String) {
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

            selectedContact = if (matchedContacts.isEmpty()) {
                Contact("", emptyList())
            } else {
                matchedContacts.first()
            }
        }
    }

    fun transferMoney() {
        if (!_uiState.value.isValid) return
        println("Transaction triggered ${_uiState.value.fullCode}")
        // TODO: Perform the money transfer (transaction service...?!)
        // TODO: Log to analytics...
    }
}

class TransferViewModelFactory(
    private val context: Context,
    private val settingsRepository: AppSettingsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransferViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransferViewModel(context, settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}