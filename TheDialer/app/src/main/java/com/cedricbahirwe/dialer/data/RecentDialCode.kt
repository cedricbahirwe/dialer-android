package com.cedricbahirwe.dialer.data

import com.cedricbahirwe.dialer.data.protocol.Identifiable
import java.util.UUID

data class RecentDialCode(
    override val id: UUID = UUID.randomUUID(),
    val detail: PurchaseDetailModel,
    var count: Int = 1
) : Identifiable<UUID> {

    val totalPrice: Int
        get() = detail.amount * count

}