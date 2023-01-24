package com.cedricbahirwe.dialer.model

import com.cedricbahirwe.dialer.model.protocol.AppNotification
import java.net.URL
import java.util.*

data class DialerLocalNotification(
    override val id: UUID,
    override val title: String,
    override val message: String,
    override val info: Map<String, Any>,
    override val imageUrl: URL?,
    override val scheduledDate: Date
) : AppNotification