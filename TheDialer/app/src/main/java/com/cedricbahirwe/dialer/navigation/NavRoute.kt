package com.cedricbahirwe.dialer.navigation


sealed class NavRoute(val path: String) {

    object MainScreen : NavRoute("MainScreen")

    object AirtimePurchase : NavRoute("Airtime Purchase")

    object Send : NavRoute("Send")

    object History : NavRoute("History")

    object MySpace: NavRoute("My Space")

    object QuickDialing: NavRoute("Quick Dialing")

    object HomeScreen : NavRoute("Home Screen")
}