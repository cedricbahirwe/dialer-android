package com.cedricbahirwe.dialer.data

import com.cedricbahirwe.dialer.data.protocol.Identifiable
import java.util.*

data class AlertDialog(
    override var id: UUID = UUID.randomUUID(),
    var title: String? = null,
    var message: String,
    var action: () -> Unit) : Identifiable<UUID> {
    constructor(): this(id = UUID.randomUUID(), title = null, message = "", action = {})
}