package com.cedricbahirwe.dialer.service

enum class AppAnalyticsEventType : AnalyticsEventType {
    SETTINGS_OPENED,
    TRANSFER_OPENED,
    AIRTIME_OPENED,
    HISTORY_OPENED,
    MY_SPACE_OPENED,
    TRANSACTION;
    override val stringValue: String
        get() = name.lowercase().replace("_", "")
}