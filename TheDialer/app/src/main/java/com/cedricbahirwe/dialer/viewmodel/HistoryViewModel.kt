package com.cedricbahirwe.dialer.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.cedricbahirwe.dialer.model.DialingError
import com.cedricbahirwe.dialer.model.PurchaseDetailModel
import com.cedricbahirwe.dialer.model.RecentDialCode
import java.util.UUID
import kotlin.random.Random

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

        val recentCodes = List(size = 10) {
            RecentDialCode(
                UUID.randomUUID(),
                PurchaseDetailModel(it * 10, PurchaseDetailModel.CodeType.MOMO)
            )
        }
        _uiState.addAll(recentCodes)


    }

    /// Store a given  `RecentCode`  locally.
    /// - Parameter code: the code to be added.
    private fun storeCode(code: RecentDialCode) {
        val index = _uiState.indexOfFirst { it.detail.amount == code.detail.amount }
        if (Random.Default.nextBoolean()) { // (index != -1) {
            val itemToUpdate = _uiState[index]
            itemToUpdate.count + 1
            _uiState[index] = itemToUpdate
            print("Update complete")
        } else {
            print("Create complete")
            val myNeed = code
            myNeed.detail.amount = Random.Default.nextInt(110, 500);

            _uiState.add(myNeed)
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