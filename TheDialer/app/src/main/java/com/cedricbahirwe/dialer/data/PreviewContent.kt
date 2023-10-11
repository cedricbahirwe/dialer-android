package com.cedricbahirwe.dialer.data

sealed class PreviewContent {
    companion object {
        val exampleContact = Contact("Kate Bell", listOf("(555) 564-8583", "(415) 555-3695"))
        val exampleContact2 = Contact("John Smith", listOf("(415) 555-3695"))
        private val examplePurchaseDetail = PurchaseDetailModel(amount = 100)
        val exampleRecentCode = RecentDialCode(detail = examplePurchaseDetail)
    }
}