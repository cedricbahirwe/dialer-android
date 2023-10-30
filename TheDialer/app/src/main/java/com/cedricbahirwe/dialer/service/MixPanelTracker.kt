package com.cedricbahirwe.dialer.service

import android.content.Context
import android.util.Log
import com.cedricbahirwe.dialer.data.Transaction
import com.cedricbahirwe.dialer.utilities.AppConstants
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MixPanelTracker private constructor(context: Context): AnalyticsTracker {
    private var mp: MixpanelAPI = MixpanelAPI.getInstance(context, AppConstants.mixPanelToken, true)

    companion object {
        @Volatile
        private var INSTANCE: MixPanelTracker? = null

        fun getInstance(context: Context): MixPanelTracker {
            return INSTANCE ?: synchronized(this) {
                val instance = MixPanelTracker(context)
                INSTANCE = instance
                instance
            }
        }
    }


    override fun logEvent(name: AnalyticsEventType, props: JSONObject) {
        mp.track(name.stringValue, props)
        mp.deviceInfo
    }

    override fun logEvent(name: AnalyticsEventType) {
        mp.track(name.stringValue)
    }

    override fun logTransaction(transaction: Transaction) {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("rw", "RW"))
        val current = formatter.format(time)

        val props = JSONObject()
        props.put("Trans ID", transaction.id)
        props.put("Trans Amount", transaction.doubleAmount)
        props.put("Trans Type", transaction.type.name)
        props.put("Trans Code", transaction.fullCode)
        props.put("Trans Date", current)
        mp.track(AppAnalyticsEventType.TRANSACTION.stringValue, props)
        print("Trans Event")
    }

    override fun logError(error: Error) {
        Log.e("TRACKING ERROR", error.toString())
        val props = JSONObject()
        props.put("Error Msg", error.toString())
        mp.track("error", props)
    }
}