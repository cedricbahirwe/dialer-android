package com.cedricbahirwe.dialer.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.cedricbahirwe.dialer.model.DialingError
import com.cedricbahirwe.dialer.model.PurchaseDetailModel
import com.cedricbahirwe.dialer.model.RecentDialCode
import java.util.UUID

class HistoryViewModel: ViewModel() {
    private val _uiState = mutableStateListOf<RecentDialCode>()
    val uiState: List<RecentDialCode> = _uiState

    init {
        retrieveHistoryCodes()
    }

    /// Retrieve all locally stored recent codes.
    private fun retrieveHistoryCodes() {
        // TODO: Retrieve stored codes
        // recentCodes = DialerStorage.shared.getSortedRecentCodes()

        val recentCodes = List(size = 15) {
            RecentDialCode(
                UUID.randomUUID(),
                PurchaseDetailModel(it * 10)
            )
        }
        _uiState.addAll(recentCodes)

    }

    /// Store a given  `RecentCode`  locally.
    /// - Parameter code: the code to be added.
    private fun storeCode(code: RecentDialCode) {
        val index = _uiState.indexOf(code)
        if (index != 1) {
            _uiState[index] = _uiState[index].copy(count = code.count + 1)
        } else {
            _uiState.add(code)
        }
        saveRecentCodesLocally()
    }

    /// Perform a quick dialing from the `HistoryRow.`
    /// - Parameter recentCode: the row code to be performed.
    fun performRecentDialing(recentCode: RecentDialCode) {
        // TODO: Perform operation

        try {
//            val result = recentCode.detail.dialCode()
            storeCode(recentCode)
        } catch (error: DialingError) {
//            Log.debug(error.message)
        }
    }

    /// Save RecentCode(s) locally.
    private fun saveRecentCodesLocally() {
        // TODO: Store all local codes
        try {
            // Save all recent codes to local storage
//            DialerStorage.shared.saveRecentCodes(recentCodes)
        } catch (error: Exception) {
            // TODO: Track Error
//            Tracker.shared.logError(error)
//            Log.debug("Could not save recent codes locally: ${error.message}")
        }
    }
}