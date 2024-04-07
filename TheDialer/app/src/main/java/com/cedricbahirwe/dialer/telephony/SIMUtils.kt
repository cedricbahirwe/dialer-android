package com.cedricbahirwe.dialer.telephony

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils

class SIMUtils private constructor(context: Context) {
    private val telephonyManagers: MutableMap<Int, TelephonyManager>
    private val telephonyManager: TelephonyManager
    private val subscriptionManager: SubscriptionManager

    private val mtnRwandaCarrierName = "MTN Rwanda"

    @get:SuppressLint("MissingPermission")
    private var subsInfoList: List<SubscriptionInfo> = ArrayList()
        get() {
            field = subscriptionManager.activeSubscriptionInfoList
            return field
        }

    // initializes telephony managers instances for both
    // single and dual sim enabled devices
    private fun initializeTelephonyManagers() {
        if (telephonyManagers.isNotEmpty()) {
            telephonyManagers.clear()
        }
        for (subsInfo in subsInfoList) {
            telephonyManagers[subsInfo.simSlotIndex] =
                telephonyManager.createForSubscriptionId(subsInfo.subscriptionId)
        }
    }


    // Returns the sim slot index of the given MTN sim
    fun getMTNRwandaSlotIndex(): Int? {
        for (subsInfo in subsInfoList) {
            if (TextUtils.equals(mtnRwandaCarrierName, subsInfo.carrierName))
                return subsInfo.simSlotIndex
        }
        return null
    }

    companion object {
        // Singleton instance of this class
        @SuppressLint("StaticFieldLeak")
        var INSTANCE: SIMUtils? = null
        fun getInstance(context: Context): SIMUtils? {
            if (INSTANCE == null) {
                synchronized(SIMUtils::class.java) {
                    if (INSTANCE == null)
                        INSTANCE = SIMUtils(context.applicationContext)
                }
            }
            return INSTANCE
        }
    }

    init {
        telephonyManagers = HashMap()
        telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        subscriptionManager =
            context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
        initializeTelephonyManagers()
    }
}
