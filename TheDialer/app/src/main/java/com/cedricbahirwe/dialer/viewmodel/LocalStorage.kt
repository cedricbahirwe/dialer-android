package com.cedricbahirwe.dialer.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import com.cedricbahirwe.dialer.model.CodePin
import com.cedricbahirwe.dialer.model.ElectricityMeter
import com.cedricbahirwe.dialer.model.RecentDialCode
import com.cedricbahirwe.dialer.model.USSDCode
import com.cedricbahirwe.dialer.utilities.LocalKeys
import java.util.*

private typealias RecentCodes = List<RecentDialCode>
private typealias ElectricityMeters = List<ElectricityMeter>
private typealias USSDCodes = List<USSDCode>

class DialerStorage(context: Context) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)


//    private val userDefaults = UserDefaults.standard

    fun saveCodePin(value: CodePin) {
        sharedPreferences.edit().putString(LocalKeys.pinCode, value.asString)
//        val data = value.let { encodeData(it) }
//        userDefaults.set(data, forKey: UserDefaults. Keys . pinCode)
    }

    private fun getCodePin(): CodePin? {
        val codePin = sharedPreferences.getString(LocalKeys.pinCode, null) ?: return null
        return CodePin(codePin)
//        return decodeData(UserDefaults.Keys.pinCode, CodePin::class.java)
    }

    fun hasSavedCodePin() = getCodePin() != null

    fun removePinCode() {
        sharedPreferences.edit().remove(LocalKeys.pinCode)
        applyChanges()
    }

    fun storeSyncDate() {
//        val syncDateKey =
        // Store the Last Sync date if it does not exist
        if (sharedPreferences.getString(LocalKeys.lastSyncDate, null) != null) return

        sharedPreferences.edit().putString(LocalKeys.lastSyncDate, Date().toString())
        applyChanges()
    }

    // Remove the existing last sync date, so it can be stored on the next app launch
    fun clearSyncDate() {
        sharedPreferences.edit().remove(LocalKeys.lastSyncDate)
    }

    // Check whether 1 month period has been reached since last sync date
    @RequiresApi(Build.VERSION_CODES.O)
    fun isSyncDateReached(): Boolean {
        val lastSyncDate = sharedPreferences.getString(LocalKeys.lastSyncDate, null) ?: return false

        val date = Date(lastSyncDate)
        // To check if 30 Days have passed
        return date.toInstant().nano / 84_400 > 30
    }

    fun saveRecentCodes(codes: RecentCodes) {
        sharedPreferences.edit().putString(LocalKeys.recentCodes, codes.toString())
        applyChanges()
    }

//    fun getRecentCodes() = decodeDatasArray<RecentCodes>(UserDefaults.Keys.recentCodes).orEmpty()

    fun saveElectricityMeters(meters: ElectricityMeters) {
        sharedPreferences.edit().putString(LocalKeys.meterNumbers, meters.toString())
        applyChanges()
    }

//    fun getMeterNumbers() =
//        decodeDatasArray<ElectricityMeters>(UserDefaults.Keys.meterNumbers).orEmpty()

    fun saveUSSDCodes(ussds: USSDCodes) {
        sharedPreferences.edit().putString(LocalKeys.customUSSDCodes, ussds.toString())
        applyChanges()
    }

//    fun getUSSDCodes() = decodeDatasArray<USSDCodes>(UserDefaults.Keys.customUSSDCodes).orEmpty()

    fun removeAllUSSDCodes() {
        sharedPreferences.edit().clear()
        applyChanges()
    }

    private fun applyChanges() {
        sharedPreferences.edit().apply()
    }
}
