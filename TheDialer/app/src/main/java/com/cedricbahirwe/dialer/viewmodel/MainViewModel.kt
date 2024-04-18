package com.cedricbahirwe.dialer.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cedricbahirwe.dialer.data.DialingError
import com.cedricbahirwe.dialer.data.PurchaseDetailModel
import com.cedricbahirwe.dialer.data.RecentDialCode
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import com.cedricbahirwe.dialer.service.AnalyticsTracker
import com.cedricbahirwe.dialer.service.AppAnalyticsEventType
import com.cedricbahirwe.dialer.service.MixPanelTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

open class MainViewModel(
    context: Context,
    private val settings: AppSettingsRepository
): ViewModel() {

    private val phoneDialer: PhoneDialer by lazy {
        PhoneDialer.getInstance(context)
    }
    private val tracker: AnalyticsTracker by lazy {
        MixPanelTracker.getInstance(context)
    }

//    val biometricsState = settings.getBiometrics
    val showWelcomeState = settings.showWelcomeView
//    val allUSSDCodes = settings.getUSSDCodes

    private val _airtimeState = MutableStateFlow(0)
    val airtimeState = _airtimeState.asStateFlow()

    val hasValidAmount: Flow<Boolean> get() = airtimeState.map { it > 0 }

    fun finishOnBoarding() {
        viewModelScope.launch {
            saveWelcomeStatus(false)
        }
    }

    fun handleNewKey(value: String) {
        var input = _airtimeState.value.toString()

        if (value == "X") {
            if (input.isNotEmpty())
                input = input.dropLast(1)
        } else {
            input += value
        }

        handleNewInput(input)
    }

    private fun handleNewInput(value: String) {
        _airtimeState.value = value.toIntOrNull() ?: 0
    }

//    suspend fun storeCode(code: RecentDialCode) {
//        settings.saveRecentCode(code)
//    }

    fun confirmPurchase() {
        val purchase = PurchaseDetailModel(_airtimeState.value)

        dialCode(purchase) { success, failure ->
            viewModelScope.launch {
                if (success != null) {
                    settings.saveRecentCode(RecentDialCode(detail = purchase))
                    _airtimeState.value = 0
                } else if (failure != null) {
                    println("Failure here ${failure.message}")
//                    Toast.makeText("", Toast.LENGTH_SHORT)
                }
            }
        }
    }
    private fun dialCode(
        purchase: PurchaseDetailModel,
        completion: (success: String?, failure: DialingError?) -> Unit
    ) {
        val fullCode = purchase.getFullUSSDCode()
        phoneDialer.dial(fullCode) {
            when (it) {
                true -> completion("Successfully Dialed", null)
                false -> completion(null, DialingError.CanNotDial())
            }
        }
    }

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

    private suspend fun saveWelcomeStatus(newValue: Boolean) {
        settings.saveWelcomeStatus(newValue)
    }

    suspend fun removeAllUSSDCodes() {
        settings.removeAllUSSDCodes()
    }

    fun trackAirtimeOpen() {
        tracker.logEvent(AppAnalyticsEventType.AIRTIME_OPENED)
    }

    fun trackMySpaceOpened() {
        tracker.logEvent(AppAnalyticsEventType.MY_SPACE_OPENED)
    }

    fun trackTransferOpen() {
        tracker.logEvent(AppAnalyticsEventType.TRANSFER_OPENED)
    }
    fun trackSettingsOpen() {
        tracker.logEvent(AppAnalyticsEventType.SETTINGS_OPENED)
    }

    fun trackHistoryOpen() {
        tracker.logEvent(AppAnalyticsEventType.HISTORY_OPENED)
    }
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