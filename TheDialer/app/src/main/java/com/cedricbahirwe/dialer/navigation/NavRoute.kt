package com.cedricbahirwe.dialer.navigation


sealed class NavRoute(val path: String) {

    object MainScreen : NavRoute("MainScreen")

    object AirtimePurchase : NavRoute("Airtime Purchase")

    object PinView : NavRoute("Pin View")

    object Send : NavRoute("Send")

    object History : NavRoute("History")

    object HomeScreen : NavRoute("Home Screen")
}