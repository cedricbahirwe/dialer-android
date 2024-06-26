package com.cedricbahirwe.dialer.data.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cedricbahirwe.dialer.data.DialerSerializer
import com.cedricbahirwe.dialer.data.RecentDialCode
import com.cedricbahirwe.dialer.utilities.LocalKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private typealias RecentCodes = List<RecentDialCode>

class AppSettingsRepository private constructor(context: Context) {

    private val context =
        context.applicationContext

//    val getBiometrics: Flow<Boolean> = context.dataStore.data.map { preferences ->
//        preferences[ALLOW_BIOMETRICS] ?: false
//    }

    val showWelcomeView: Flow<Boolean> = context.dataStore.data.map {
        it[SHOW_WELCOME_VIEW] ?: true
    }

//    val getSyncDate: Flow<String> = context.dataStore.data.map {
//        it[SYNC_DATE] ?: ""
//    }

    val getRecentCodes: Flow<RecentCodes> = context.dataStore.data.map {
        val items: Set<String> = it[RECENT_CODES] ?: emptySet()
        items.map { item -> DialerSerializer.fromJson(item) }
    }

//    val getUSSDCodes: Flow<Set<String>> = context.dataStore.data.map {
//        it[ALL_USSD_CODES] ?: emptySet()
//    }

//    suspend fun saveBiometricsStatus(status: Boolean) {
//        context.dataStore.edit { preferences ->
//            preferences[ALLOW_BIOMETRICS] = status
//        }
//    }

    suspend fun saveWelcomeStatus(status: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_WELCOME_VIEW] = status
        }
    }

//    suspend fun storeSyncDate() {
//        // Store the Last Sync date if it does not exist
//        if (getSyncDate.single().isEmpty()) {
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//            val currentDate = Date()
//            val dateString = dateFormat.format(currentDate)
//            context.dataStore.edit {
//                it[SYNC_DATE] = dateString
//            }
//        }
//    }

    // Remove the existing last sync date, so it can be stored on the next app launch
//    suspend fun clearSyncDate() {
//        context.dataStore.edit {
//            it[SYNC_DATE] = ""
//        }
//    }

    suspend fun removeAllUSSDCodes() {
        context.dataStore.edit {
            it[ALL_USSD_CODES] = emptySet()
        }
    }

    // Check whether 1 month period has been reached since last sync date
//    suspend fun isSyncDateReached(): Boolean {
//        val lastSyncDateString = getSyncDate.single().takeIf { it.isNotEmpty() } ?: return false
//
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        try {
//            val lastSyncDate = dateFormat.parse(lastSyncDateString).takeIf { it != null } ?: return  false
//            val currentDate = Date()
//
//            // Calculate the difference in days
//            val differenceInMillis = currentDate.time - lastSyncDate.time
//            val differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis)
//
//            // Check if 30 days have passed
//            return differenceInDays >= MAX_DAYS_BEFORE_SYNC
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return false
//        }
//    }

    suspend fun saveRecentCode(code: RecentDialCode) {
        context.dataStore.edit { preferences ->
            val recentCodes = getRecentCodes.firstOrNull() ?: emptyList()
            val indexToUpdate = recentCodes.indexOfFirst { it.detail.amount == code.detail.amount }

            val resultItems: List<RecentDialCode> = if (indexToUpdate == -1) {
                recentCodes + code
            } else {
                val items = recentCodes.toMutableList()
                items[indexToUpdate].count += 1
                items.toList()
            }

            val resultItemsSet = resultItems.map { DialerSerializer.toJson(it) }.toSet()
            preferences[RECENT_CODES] = resultItemsSet
        }
    }
//    suspend fun saveRecentCodes(codes: RecentCodes) {
//        context.dataStore.edit {
//            it[RECENT_CODES] = codes.map { item -> DialerSerializer.toJson(item) }.toSet()
//        }
//    }

//    suspend fun saveUSSDCode(ussd: USSDCode) {
//        context.dataStore.edit {
//            it[ALL_USSD_CODES] = getUSSDCodes.single() + ussd.toString()
//        }
//    }

//    suspend fun saveUSSDCodes(ussds: USSDCodes) {
//        context.dataStore.edit {
//            it[ALL_USSD_CODES] = ussds.map { item -> item.toString() }.toSet()
//        }
//    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: AppSettingsRepository? = null

        private val Context.dataStore by preferencesDataStore("appSettings")
//        private val ALLOW_BIOMETRICS = booleanPreferencesKey(LocalKeys.ALL)
//        private  val SYNC_DATE = stringPreferencesKey(LocalKeys.lastSyncDate)

        private  val ALL_USSD_CODES = stringSetPreferencesKey(LocalKeys.CUSTOM_USSD_CODES)
        private  val RECENT_CODES = stringSetPreferencesKey(LocalKeys.RECENT_CODES)

        private val SHOW_WELCOME_VIEW = booleanPreferencesKey(LocalKeys.SHOW_WELCOME_VIEW)
//        const val MAX_DAYS_BEFORE_SYNC = 30

        fun getInstance(context: Context): AppSettingsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = AppSettingsRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}