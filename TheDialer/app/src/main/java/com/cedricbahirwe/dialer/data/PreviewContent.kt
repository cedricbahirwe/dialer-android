package com.cedricbahirwe.dialer.data

sealed class PreviewContent {
    companion object {
        
        val exampleContact2 = Contact("John Smith", mutableListOf("(415) 555-3695"))
        private val examplePurchaseDetail = PurchaseDetailModel(amount = 100)
        val exampleRecentCode = RecentDialCode(detail = examplePurchaseDetail)
    }
}