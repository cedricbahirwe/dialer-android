package com.cedricbahirwe.dialer.model

import com.cedricbahirwe.dialer.model.protocol.Identifiable
import java.util.*

data class RecentDialCode(
    override val id: UUID = UUID.randomUUID(),
    var detail: PurchaseDetailModel,
    var count: Int = 1
) : Identifiable<UUID> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as RecentDialCode
        return id == that.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    val totalPrice: Int
        get() = detail.amount * count

    fun increaseCount() {
        count += 1
    }
}

