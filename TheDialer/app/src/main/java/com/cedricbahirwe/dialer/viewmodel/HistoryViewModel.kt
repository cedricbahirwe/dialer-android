package com.cedricbahirwe.dialer.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cedricbahirwe.dialer.data.RecentDialCode
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HistoryViewModel(
    context: Context,
    settings: AppSettingsRepository
): ViewModel() {

    // TODO: This could be an interface
    private val phoneDialer: PhoneDialer by lazy {
        PhoneDialer.getInstance(context)
    }

    private val codePin = settings.getCodePin
    // Retrieve all locally stored recent codes.
    val recentCodes = settings.getRecentCodes

//    val allUSSDCodes = settings.getUSSDCodes

    /// Perform a quick dialing from the `HistoryRow.`
    /// - Parameter recentCode: the row code to be performed.
    fun performRecentDialing(recentCode: RecentDialCode) {
        viewModelScope.launch {
            val fullCode = recentCode.detail.getFullUSSDCode(codePin.firstOrNull())
            phoneDialer.dial(fullCode) {
                when (it) {
                    true -> Log.d("PhoneDialer", "Successfully Dialed : $fullCode")
                    false -> Log.e("PhoneDialer", "Failed to dial : $fullCode")
                }
            }
        }
    }
}

class HistoryViewModelFactory(
    private val context: Context,
    private val settingsRepository: AppSettingsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(context, settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}