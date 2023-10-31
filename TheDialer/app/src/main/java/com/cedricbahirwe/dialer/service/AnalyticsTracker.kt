package com.cedricbahirwe.dialer.service

import com.cedricbahirwe.dialer.data.Transaction
import org.json.JSONObject

interface AnalyticsTracker {
    fun logEvent(name: AnalyticsEventType, props: JSONObject)
    fun logEvent(name: AnalyticsEventType)
    fun logTransaction(transaction: Transaction)
    fun logError(error: Error)
}