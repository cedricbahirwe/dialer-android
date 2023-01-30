package com.cedricbahirwe.dialer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cedricbahirwe.dialer.DashBoardContainer
import com.cedricbahirwe.dialer.screens.*

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.MainScreen.path
    ) {

        addPinViewScreen(navController, this)

        addSendScreen(navController, this)

        addHistoryScreen(navController, this)

        addAirtimePurchaseScreen(navController, this)

        addMainScreen(navController, this)

        addHomeScreen(navController, this)
    }
}

fun addHomeScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.HomeScreen.path) {
        DashBoardContainer()
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

fun addAirtimePurchaseScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.AirtimePurchase.path) {
        PurchaseDetail()
    }
}

fun addHistoryScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.History.path) {
        RecentCodesList()
    }
}

fun addSendScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Send.path) {
        FieldsContainer()
    }
}

fun addPinViewScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.PinView.path) {
        PinView(
            input = "Android"
        )
    }
}
