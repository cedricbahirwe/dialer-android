package com.cedricbahirwe.dialer.model

import com.cedricbahirwe.dialer.model.protocol.Identifiable
import java.util.*

data class AlertDialog(
    override var id: UUID = UUID.randomUUID(),
    var title: String? = null,
    var message: String,
    var action: () -> Unit) : Identifiable<UUID> {
    constructor(): this(id = UUID.randomUUID(), title = null, message = "", action = {})
}