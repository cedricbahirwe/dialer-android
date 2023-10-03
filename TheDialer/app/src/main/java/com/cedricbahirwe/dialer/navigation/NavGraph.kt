package com.cedricbahirwe.dialer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cedricbahirwe.dialer.QuickDialingView
import com.cedricbahirwe.dialer.screens.DashBoardContainer
import com.cedricbahirwe.dialer.screens.*

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

        addMainScreen(navController, this)

    }
}

fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.HomeScreen.path) {
        DashBoardContainer(
            navController = navController
        )
    }
}

fun addMainScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.MainScreen.path) {
        MainHomeScreen(
            navController = navController
        )
    }
}

fun addAirtimePurchaseScreen(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.AirtimePurchase.path) {
        PurchaseDetail()
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

fun addHistoryScreen(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.History.path) {
        RecentCodesList()
    }
}

fun addSendScreen(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavRoute.Send.path) {
        TransferView()
    }
}