package com.cedricbahirwe.dialer.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cedricbahirwe.dialer.data.CodePin
import com.cedricbahirwe.dialer.data.DialingError
import com.cedricbahirwe.dialer.data.PurchaseDetailModel
import com.cedricbahirwe.dialer.data.RecentDialCode
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class EditedField {
    AMOUNT, PIN
}
data class PurchaseUiState (
    val amount: Int = 0,
    val pin: String = "",
    val editedField: EditedField = EditedField.AMOUNT
)

open class MainViewModel(
    context: Context,
    private val settings: AppSettingsRepository
): ViewModel() {
    private val phoneDialer = PhoneDialer.getInstance(context)

    val biometricsState = settings.getBiometrics
    val getCodePin = settings.getCodePin
    val allUSSDCodes = settings.getUSSDCodes

    private val _uiState = MutableStateFlow(PurchaseUiState())
    val uiState: StateFlow<PurchaseUiState> = _uiState.asStateFlow()

    val hasValidAmount: Boolean get() = _uiState.value.amount > 0
    val isPinCodeValid: Boolean get() = _uiState.value.pin.length == 5

    fun shouldShowDeleteBtn() : Boolean {
        return when (_uiState.value.editedField) {
            EditedField.AMOUNT -> {
                hasValidAmount
            }

            EditedField.PIN-> {
                _uiState.value.pin.isNotEmpty()
            }
        }
    }

    fun handleNewKey(value: String) {
        var input = if (_uiState.value.editedField == EditedField.PIN) _uiState.value.pin else _uiState.value.amount.toString()

        if (value == "X") {
            if (input.isNotEmpty())
                input = input.dropLast(1)
        } else {
            input += value
        }

        handleNewInput(input)
    }

    private fun handleNewInput(value: String) {
        when (_uiState.value.editedField) {
            EditedField.AMOUNT -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        amount = value.toIntOrNull() ?: 0
                    )
                }
            }
            EditedField.PIN-> {
                _uiState.update { currentState ->
                    currentState.copy(
                        pin = value.take(5)
                    )
                }
            }
        }
    }

    fun updateEditedField(newField: EditedField) {
        _uiState.update { currentState ->
            currentState.copy(
                editedField = newField
            )
        }
    }

//    suspend fun storeCode(code: RecentDialCode) {
//        settings.saveRecentCode(code)
//    }

    private fun getOptionalCodePin(): CodePin? {
        return try {
            CodePin(_uiState.value.pin)
        } catch (e: Exception) {
            println("Found Error with Pin")
            e.printStackTrace()
            return null
        }
    }

    fun getCodePin(): CodePin {
        return try {
            CodePin(_uiState.value.pin)
        } catch (e: Exception) {
            println("Found Error with Pin")
            e.printStackTrace()
            throw  e
        }
    }
    fun confirmPurchase() {
        val purchase = PurchaseDetailModel(_uiState.value.amount)

        dialCode(purchase) { success, failure ->
            viewModelScope.launch {
                if (success != null) {
                    settings.saveRecentCode(RecentDialCode(detail = purchase))
                    _uiState.update {
                        it.copy(amount = 0)
                    }
                } else if (failure != null) {
                    println(failure.message)
//                    Toast.makeText("", Toast.LENGTH_SHORT)
                }
            }
        }

        println("Purchasing ${purchase.getFullUSSDCode(null)}")
    }
    private fun dialCode(
        purchase: PurchaseDetailModel,
        completion: (success: String?, failure: DialingError?) -> Unit
    ) {
        val fullCode = purchase.getFullUSSDCode(getOptionalCodePin())
        phoneDialer.dial(fullCode) {
            when (it) {
                true -> completion("Successfully Dialed", null)
                false -> completion(null, DialingError.CanNotDial())
            }
        }
    }

//    private fun getFullUSSDCode(purchase: PurchaseDetailModel): String {
//        return purchase.getFullUSSDCode(getCodePin())
//    }

//    private fun performQuickDial(quickCode: DialerQuickCode) {
//        phoneDialer.dial(quickCode.ussd)
//    }

    /// Perform a quick dialing from the `History View Row.`
    /// - Parameter recentCode: the row code to be performed.
//    fun performRecentDialing(recentCode: RecentDialCode) {
//        dialCode(recentCode.detail) { success, failure ->
//            viewModelScope.launch {
//                if (success != null) {
//                    println("Code saved")
//                    settings.saveRecentCode(recentCode)
//                } else if (failure != null) {
//                    println(failure.message)
//                }
//            }
//        }
//    }

    /* MARK: Extension used for Quick USSD actions. */
//    fun checkMobileWalletBalance() {
//        try {
//            val myPin = getCodePin()
//            performQuickDial(DialerQuickCode.MobileWalletBalance(code = myPin))
//        } catch (e: Exception) {
//            performQuickDial(DialerQuickCode.MobileWalletBalance(code = null))
//        }
//    }

//    suspend fun saveUSSDCodesLocally(codes: List<USSDCode>) {
//        settings.saveUSSDCodes(codes)
//    }

    // Local Storage
    suspend fun saveCodePin(codePin: CodePin) {
        settings.saveCodePin(codePin)
    }

    suspend fun saveBiometricsStatus(newValue: Boolean) {
        settings.saveBiometricsStatus(newValue)
    }

    suspend fun removeAllUSSDCodes() {
        settings.removeAllUSSDCodes()
    }

    suspend fun removePinCode() {
        settings.removePinCode()
        _uiState.update {
            it.copy(pin = "")
        }
    }



//    private fun shareLink(context: Context, link: String) {
//        val intent = Intent(Intent.ACTION_SEND).apply {
//            type = "text/plain"
//            putExtra(Intent.EXTRA_TEXT, link)
//        }
//
//        context.startActivity(Intent.createChooser(intent, "Share Link"))
//    }
}

class MainViewModelFactory(
    private val context: Context,
    private val settingsRepository: AppSettingsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(context, settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}