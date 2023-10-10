package com.cedricbahirwe.dialer.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cedricbahirwe.dialer.data.CodePin
import com.cedricbahirwe.dialer.data.ElectricityMeter
import com.cedricbahirwe.dialer.data.RecentDialCode
import com.cedricbahirwe.dialer.data.USSDCode
import com.cedricbahirwe.dialer.utilities.LocalKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

private typealias RecentCodes = List<RecentDialCode>
private typealias ElectricityMeters = List<ElectricityMeter>
private typealias USSDCodes = List<USSDCode>

class AppSettingsRepository(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore("appSettings")
        private val ALLOW_BIOMETRICS = booleanPreferencesKey(LocalKeys.allowBiometrics)
        private val PIN_CODE = stringPreferencesKey(LocalKeys.pinCode)
        private  val SYNC_DATE = stringPreferencesKey(LocalKeys.lastSyncDate)

        private  val ALL_USSD_CODES = stringSetPreferencesKey(LocalKeys.customUSSDCodes)
        private  val RECENT_CODES = stringSetPreferencesKey(LocalKeys.recentCodes)
        private  val METER_NUMBERS = stringSetPreferencesKey(LocalKeys.meterNumbers)

    }

    val getBiometrics: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[ALLOW_BIOMETRICS] ?: false
    }

    val getCodePin: Flow<CodePin?> = context.dataStore.data.map { it ->
        it[PIN_CODE]
            ?.takeIf { pinCodeString -> pinCodeString.isNotEmpty() }
            ?.let { CodePin(it) }
    }

    val getSyncDate: Flow<String> = context.dataStore.data.map {
        it[SYNC_DATE] ?: ""
    }

    val getRecentCodes: Flow<Set<String>> = context.dataStore.data.map {
        it[RECENT_CODES] ?: emptySet()
    }

    val getUSSDCodes: Flow<Set<String>> = context.dataStore.data.map {
        it[ALL_USSD_CODES] ?: emptySet()
    }

    val getMeterNumbers: Flow<Set<String>> = context.dataStore.data.map {
        it[METER_NUMBERS] ?:  emptySet()
    }

    suspend fun saveBiometricsStatus(status: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ALLOW_BIOMETRICS] = status
        }
    }

    suspend fun saveCodePin(value: CodePin) {
        context.dataStore.edit {
            it[PIN_CODE] = value.asString
        }
    }

    suspend fun removePinCode() {
        context.dataStore.edit {
            it[PIN_CODE] = ""
        }
    }

    suspend fun storeSyncDate() {
        // Store the Last Sync date if it does not exist
        if (getSyncDate.single().isEmpty()) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val currentDate = Date()
            val dateString = dateFormat.format(currentDate)
            context.dataStore.edit {
                it[SYNC_DATE] = dateString
            }
        }
    }

    // Remove the existing last sync date, so it can be stored on the next app launch
    suspend fun clearSyncDate() {
        context.dataStore.edit {
            it[SYNC_DATE] = ""
        }
    }

    suspend fun removeAllUSSDCodes() {
        context.dataStore.edit {
            it[ALL_USSD_CODES] = emptySet()
        }
    }

    // Check whether 1 month period has been reached since last sync date
    suspend fun isSyncDateReached(): Boolean {
        val lastSyncDateString = getSyncDate.single().takeIf { it.isNotEmpty() } ?: return false

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        try {
            val lastSyncDate = dateFormat.parse(lastSyncDateString).takeIf { it != null } ?: return  false
            val currentDate = Date()
            // To check if 30 Days have passed

            // Calculate the difference in days
            val differenceInMillis = currentDate.time - lastSyncDate.time
            val differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis)

            // Check if 30 days have passed
            return differenceInDays >= 30
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun saveRecentCodes(codes: RecentCodes) {
        context.dataStore.edit { it ->
            it[RECENT_CODES] = codes.map { item -> item.toString() }.toSet()
        }
    }

    suspend fun saveElectricityMeters(meters: ElectricityMeters) {
        context.dataStore.edit { it ->
            it[METER_NUMBERS] = meters.map { item -> item.toString() }.toSet()
        }
    }

    suspend fun saveUSSDCodes(ussds: USSDCodes) {
        context.dataStore.edit { it ->
            it[ALL_USSD_CODES] = ussds.map { item -> item.toString() }.toSet()
        }
    }
}