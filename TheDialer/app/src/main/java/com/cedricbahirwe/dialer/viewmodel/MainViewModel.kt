package com.cedricbahirwe.dialer.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
//    var pinCode: CodePin? = DialerStorage.shared.getCodePin()
//    var hasReachSync = DialerStorage.shared.isSyncDateReached()
//        set(newValue) {
//            if (!newValue) {
//                DialerStorage.shared.clearSyncDate()
//            }
//            field = newValue
//        }
//
//    var showHistorySheet = false
//    var showSettingsSheet = false

//    val estimatedTotalPrice: Int
//        get() = recentCodes.sumOf { it.totalPrice }
//
//    var purchaseDetail = PurchaseDetailModel()
//    var recentCodes = mutableListOf<RecentDialCode>()
//    var elecMeters = mutableListOf<ElectricityMeter>()
//    var ussdCodes = mutableListOf<USSDCode>()
//
//    private fun storeCode(code: RecentDialCode) {
//        val index = recentCodes.indexOfFirst { it.detail.amount == code.detail.amount }
//        if (index != -1) {
////            recentCodes[index].increaseCount()
//        } else {
//            recentCodes.add(code)
//        }
//        saveRecentCodesLocally()
//    }
//
//    fun containsMeter(number: String): Boolean {
//        val meter = try {
//            ElectricityMeter(number)
//        } catch (e: Exception) {
//            return false
//        }
//        return elecMeters.contains(meter)
//    }
//
//    fun saveRecentCodesLocally() {
//        try {
//            DialerStorage.shared.saveRecentCodes(recentCodes)
//        } catch (e: Exception) {
//            println("Could not save recent codes locally: ${e.message}")
//        }
//    }
//
//    fun removePin() {
//        DialerStorage.shared.removePinCode()
//        pinCode = null
//    }

//    fun hasStoredCodePin(): Boolean {
//        return DialerStorage.shared.hasSavedCodePin()
//    }
//
//    fun retrieveCodes() {
//        recentCodes = DialerStorage.shared.getRecentCodes().toMutableList()
//    }
//
    fun confirmPurchase() {
//        val purchase = purchaseDetail
//        dialCode(purchase) { success, failure ->
//            if (success != null) {
//                // Handle success
//                this.storeCode(RecentDialCode(detail = purchase))
//                this.purchaseDetail = PurchaseDetailModel()
//            } else if (failure != null) {
//                // Handle failure
//                print(failure.message)
//            }
//        }
    }
//
//    fun deletePastCode(offSets: Array<RecentDialCode>) {
//        recentCodes.removeAll { offSets.contains(it) }
//        saveRecentCodesLocally()
//    }
//
//    fun saveCodePin(value: CodePin) {
//        pinCode = value
//        try {
//            DialerStorage.shared.saveCodePin(value)
//        } catch (e: Exception) {
//            println("Storage: ${e.message}")
//        }
//    }
//
//    private fun dialCode(
//        purchase: PurchaseDetailModel,
//        completion: (success: String?, failure: DialingError?) -> Unit
//    ) {
//        val fullCode = getFullUSSDCode(purchase)
//        PhoneDialer.shared.dial(fullCode) {
//            when (it) {
//                true -> completion("Successfully Dialed", null)
//                false -> completion(null, DialingError.CanNotDial())
//            }
//        }
//    }
//
//    private fun getFullUSSDCode(purchase: PurchaseDetailModel): String {
//        val code: String = if (pinCode != null && pinCode.toString().count() >= 5) {
//            pinCode.toString()
//        } else {
//            ""
//        }
//        return purchase.getDialCode(code)
//    }

//    fun getPurchaseDetailUSSDCode() {
//        getFullUSSDCode(purchaseDetail)
//    }
//
//    // Returns a `RecentDialCode` that matches the input identifier.
//    fun recentDialCode(identifier: String): RecentDialCode? {
//        return recentCodes.firstOrNull { it.id.toString() == identifier }
//    }
//
//    private fun performQuickDial(quickCode: DialerQuickCode) {
//        PhoneDialer.shared.dial(quickCode.ussd)
//    }

    /// Perform a quick dialing from the `History View Row.`
    /// - Parameter recentCode: the row code to be performed.
//    fun performRecentDialing(recentCode: RecentDialCode) {
//        dialCode(recentCode.detail) { success, failure ->
//            if (success != null) {
//                this.storeCode(recentCode)
//            } else if (failure != null) {
//                print(failure.message)
//            }
//        }
//    }
//
//    fun showSettingsView() {
//        showSettingsSheet = true
//    }
//
//    fun dismissSettingsView() {
//        showSettingsSheet = false
//    }
//
//    /* MARK: Extension used for Quick USSD actions. */
//
//    fun checkMobileWalletBalance() {
//        performQuickDial(DialerQuickCode.MobileWalletBalance(code = pinCode))
//    }
//
//    fun getElectricity(meterNumber: String, amount: Int) {
//        val number = meterNumber.replace(" ", "")
//        performQuickDial(
//            DialerQuickCode.Electricity(
//                meter = number,
//                amount = amount,
//                code = pinCode
//            )
//        )
//    }
//
//    /* MARK: Electricity Storage */
//
//    // Store a given  `MeterNumber`  locally.
//    fun storeMeter(number: ElectricityMeter) {
//        if (!elecMeters.any { it.id == number.id }) {
//            elecMeters.add(number)
//            saveMeterNumbersLocally(elecMeters)
//        }
//    }

    // Save MeterNumber(s) locally.
//    private fun saveMeterNumbersLocally(meters: List<ElectricityMeter>) {
//        try {
//            DialerStorage.shared.saveElectricityMeters(meters)
//        } catch (e: Exception) {
//            println("Could not save meter numbers locally: ${e.localizedMessage}")
//        }
//    }

    // Retrieve all locally stored Meter Numbers codes
//    fun retrieveMeterNumbers() {
//        elecMeters = DialerStorage.shared.getMeterNumbers().toMutableList()
//    }
//
//    fun deleteMeter(offsets: List<ElectricityMeter>) {
//        elecMeters.removeAll { offsets.contains(it) }
//        saveMeterNumbersLocally(elecMeters)
//    }
//
//    /* MARK: Custom USSD Storage */
//
//    // Store a given `USSDCode` locally.
//    fun storeUSSD(code: USSDCode) {
//        if (!ussdCodes.contains(code)) {
//            ussdCodes.add(code)
//            saveUSSDCodesLocally(ussdCodes)
//        }
//    }
//
//    fun updateUSSD(code: USSDCode) {
//        val index = ussdCodes.indexOf(code)
//        if (index != -1) {
//            ussdCodes[index] = code
//        }
//        saveUSSDCodesLocally(ussdCodes)
//    }
//
//    // Save USSDCode(s) locally.
//    private fun saveUSSDCodesLocally(codes: List<USSDCode>) {
//        try {
//            DialerStorage.shared.saveUSSDCodes(codes)
//        } catch (e: Exception) {
//            println("Could not save ussd codes locally: ${e.localizedMessage}")
//        }
//    }
//
//    // Retrieve all locally stored Meter Numbers codes
//    fun retrieveUSSDCodes() {
//        ussdCodes = DialerStorage.shared.getUSSDCodes().toMutableList()
//    }
//
//    fun deleteUSSD(offsets: List<USSDCode>) {
//        ussdCodes.removeAll { offsets.contains(it) }
//        saveUSSDCodesLocally(ussdCodes)
//    }
//
//    fun removeAllUSSDs() {
//        DialerStorage.shared.removeAllUSSDCodes()
//        ussdCodes.clear()
//    }
}