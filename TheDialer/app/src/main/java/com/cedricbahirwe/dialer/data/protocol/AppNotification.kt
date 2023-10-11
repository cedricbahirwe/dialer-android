package com.cedricbahirwe.dialer.data.protocol

import java.net.URL
import java.util.UUID
import java.util.Date

interface AppNotification {
    val id: UUID
    val title: String
    val message: String
    val info: Map<String, Any>
    val imageUrl: URL?
    val scheduledDate: Date
}