package com.cedricbahirwe.dialer.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.cedricbahirwe.dialer.data.PurchaseDetailModel
import com.cedricbahirwe.dialer.data.RecentDialCode
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.runBlocking
import java.util.UUID
import kotlin.random.Random

class HistoryViewModel(private val settings: AppSettingsRepository): ViewModel() {
    private val codePin = settings.getCodePin
    val recentCodes = settings.getRecentCodes
    val allUSSDCodes = settings.getUSSDCodes

    private val _uiState = mutableStateListOf<RecentDialCode>()
    val uiState: List<RecentDialCode> = _uiState

    init {
        retrieveHistoryCodes()
    }

    // Save RecentCode(s) locally.
    private suspend fun saveRecentCodesLocally() {
        val list = emptyList<RecentDialCode>()
        settings.saveRecentCodes(list)
    }

    // Retrieve all locally stored recent codes.
    private fun retrieveHistoryCodes() {
        // TODO: Retrieve stored codes
        // recentCodes = DialerStorage.shared.getSortedRecentCodes()

        val recentCodes = List(size = 6) {
            RecentDialCode(
                UUID.randomUUID(),
                PurchaseDetailModel(Random.Default.nextInt(0, 2_000))
            )
        }
        _uiState.addAll(recentCodes)
        runBlocking {
            settings.saveRecentCodes(recentCodes)
        }
    }

    private suspend fun storeCode(code: RecentDialCode) {
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
        runBlocking {
            val fullCode = recentCode.detail.getFullUSSDCode(codePin.singleOrNull())
            PhoneDialer.shared.dial(fullCode) {
                when (it) {
                    true -> println("Successfully Dialed")
                    false -> println("Failed to dial")
                }
            }
        }
    }
}