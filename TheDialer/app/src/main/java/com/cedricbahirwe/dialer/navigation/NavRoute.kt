package com.cedricbahirwe.dialer.navigation


sealed class NavRoute(val path: String) {
    object HomeScreen : NavRoute("Home Screen")

    object AirtimePurchase : NavRoute("Airtime Purchase")

    object Send : NavRoute("Send")

    object History : NavRoute("History")

    object MySpace: NavRoute("My Space")

    object QuickDialing: NavRoute("Quick Dialing")

    object Settings : NavRoute("Settings Screen")
}