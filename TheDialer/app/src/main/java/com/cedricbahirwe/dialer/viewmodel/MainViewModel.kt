package com.cedricbahirwe.dialer.viewmodel

import android.content.res.Resources
import com.cedricbahirwe.dialer.model.*

abstract class UtilitiesDelegate {
    abstract fun didSelectOption(withCode: DialerQuickCode)
}

abstract class MainViewModel {
    var pinCode: CodePin? = DialerStorage.shared.getCodePin()
    var hasReachSync = DialerStorage.shared.isSyncDateReached()
        set(newValue) {
            if (!newValue) {
                DialerStorage.shared.clearSyncDate()
            }
            field = newValue
        }

    abstract var utilityDelegate: UtilitiesDelegate?
    var showHistorySheet = false
    var showSettingsSheet = false

    val estimatedTotalPrice: Int
        get() = recentCodes.map { it.totalPrice }.sum()

    var purchaseDetail = PurchaseDetailModel()
    var recentCodes = mutableListOf<RecentDialCode>()
    var elecMeters = mutableListOf<ElectricityMeter>()
    var ussdCodes = mutableListOf<USSDCode>()

    private fun storeCode(code: RecentDialCode) {
        val index = recentCodes.indexOfFirst { it.detail.amount == code.detail.amount }
        if (index != -1) {
            recentCodes[index].increaseCount()
        } else {
            recentCodes.add(code)
        }
        saveRecentCodesLocally()
    }

    fun containsMeter(number: String): Boolean {
        val meter = try {
            ElectricityMeter(number)
        } catch (e: Exception) {
            return false
        }
        return elecMeters.contains(meter)
    }

    fun saveRecentCodesLocally() {
        try {
            DialerStorage.shared.saveRecentCodes(recentCodes)
        } catch (e: Exception) {
            println("Could not save recent codes locally: ${e.message}")
        }
    }

    fun removePin() {
        DialerStorage.shared.removePinCode()
        pinCode = null
    }

    fun hasStoredCodePin(): Boolean {
        return DialerStorage.shared.hasSavedCodePin()
    }

    fun retrieveCodes() {
        recentCodes = DialerStorage.shared.getRecentCodes().toMutableList()
    }

    fun confirmPurchase() {
        val purchase = purchaseDetail
        dialCode(purchase) { p
            when (this) {
                is Result.Success -> {
                    storeCode(RecentDialCode(purchase))
                    purchaseDetail = PurchaseDetailModel()
                }
                is Result.Error -> println(this.message)
            }
        }
    }

    fun deletePastCode(offSets: Array<RecentDialCode>) {
        recentCodes.removeAll { offSets.contains(it) }
        saveRecentCodesLocally()
    }

    fun saveCodePin(value: CodePin) {
        pinCode = value
        try {
            DialerStorage.shared.saveCodePin(value)
        } catch (e: Exception) {
            println("Storage: ${e.message}")
        }
    }

    private fun dialCode(
        purchase: PurchaseDetailModel,
        completion: (Result<String, DialingError>) -> Unit
    ) {
        val newUrl = getFullUSSDCode(purchase)
        PhoneDialer.dial
        if let telUrl = PhoneDialer  URL(string: "tel://\(newUrl)"),
        UIApplication.shared.canOpenURL(telUrl) {
            UIApplication.shared.open(telUrl, options: [:], completionHandler: { _ in
                completion(.success("Successfully Dialed"))
        })

        } else {
            // Can not dial this code
            completion(.failure(.canNotDial))
        }
    }

    private sealed class DialingError(override val message: String) : Throwable() {
        private companion object {
            val context: Resources = Resources.getSystem()
        }

        class CanNotDial : DialingError("Can not dial this code")
        class UnknownFormat(format: String) : DialingError("Can not decode this format: $format")
        class EmptyPin : DialingError("Pin Code not found, configure pin and try again")
    }
}