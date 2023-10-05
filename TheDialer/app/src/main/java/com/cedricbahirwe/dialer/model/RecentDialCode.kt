package com.cedricbahirwe.dialer.model

import com.cedricbahirwe.dialer.model.protocol.Identifiable
import java.util.*

data class RecentDialCode(
    override val id: UUID = UUID.randomUUID(),
    val detail: PurchaseDetailModel,
    val count: Int = 1
) : Identifiable<UUID> {

    val totalPrice: Int
        get() = detail.amount * count

}

