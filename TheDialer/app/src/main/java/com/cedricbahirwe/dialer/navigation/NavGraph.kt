package com.cedricbahirwe.dialer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cedricbahirwe.dialer.BuildConfig
import com.cedricbahirwe.dialer.QuickDialingView
import com.cedricbahirwe.dialer.screens.AboutScreen
import com.cedricbahirwe.dialer.screens.DashBoardContainer
import com.cedricbahirwe.dialer.screens.HistoryView
import com.cedricbahirwe.dialer.screens.PurchaseDetailView
import com.cedricbahirwe.dialer.screens.SettingsScreen
import com.cedricbahirwe.dialer.screens.TransferView

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.HomeScreen.path
    ) {

        addHomeScreen(navController, this)

        addAirtimePurchaseScreen(this)

        addSendScreen(this)

        addHistoryScreen(this)

        addQuickDialingScreen(navController, this)

        addSettingsScreen(navController, this)

        addAboutScreen(this)
    }
}

private fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.HomeScreen.path) {
        DashBoardContainer(
            navController = navController
        )
    }
}

private fun addAirtimePurchaseScreen(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.AirtimePurchase.path) {
        PurchaseDetailView()
    }
}

private fun addQuickDialingScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(NavRoute.QuickDialing.path) {
        QuickDialingView(navController = navController)
    }
}

private fun addHistoryScreen(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.History.path) {
        HistoryView()
    }
}

private fun addSendScreen(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.Send.path) {
        TransferView()
    }
}

private fun addAboutScreen(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.AboutApp.path) {
        AboutScreen(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}

private fun addSettingsScreen(
    navController: NavController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Settings.path) {
        SettingsScreen(navController = navController)
    }
}