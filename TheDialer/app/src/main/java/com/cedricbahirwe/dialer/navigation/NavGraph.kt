package com.cedricbahirwe.dialer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cedricbahirwe.dialer.BuildConfig
import com.cedricbahirwe.dialer.data.Contact
import com.cedricbahirwe.dialer.screens.AboutScreen
import com.cedricbahirwe.dialer.screens.ContactsList
import com.cedricbahirwe.dialer.screens.DashBoardContainer
import com.cedricbahirwe.dialer.screens.HistoryView
import com.cedricbahirwe.dialer.screens.PurchaseDetailView
import com.cedricbahirwe.dialer.screens.QuickDialingView
import com.cedricbahirwe.dialer.screens.SettingsScreen
import com.cedricbahirwe.dialer.screens.TransferView
import com.cedricbahirwe.dialer.viewmodel.TransferViewModel
import com.cedricbahirwe.dialer.viewmodel.TransferViewModelFactory

@Composable
fun NavGraph(
    navController: NavHostController,
    transferViewModel: TransferViewModel = viewModel(
        factory = TransferViewModelFactory(LocalContext.current.applicationContext)
    )
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.HomeScreen.path
    ) {

        addHomeScreen(navController, this)

        addAirtimePurchaseScreen(this)

        addSendScreen(navController, this, transferViewModel)

        addHistoryScreen(this)

        addQuickDialingScreen(navController, this)

        addSettingsScreen(navController, this)

        addAboutScreen(navController, this)

        addContactsListScreen(navController, this, transferViewModel, onSelectContact = {
            transferViewModel.cleanPhoneNumber(it)
        })
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

private fun addSendScreen(
    navController: NavController,
    navGraphBuilder: NavGraphBuilder,
    transferViewModel: TransferViewModel,

) {
    navGraphBuilder.composable(route = NavRoute.Send.path) {
        TransferView(navController, transferViewModel, openContactList = {
            navController.navigate(NavRoute.ContactsList.path)
        })
    }
}

private fun addAboutScreen(
    navController: NavController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.AboutApp.path) {
        AboutScreen(
            navController,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )
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

private fun addContactsListScreen(
    navController: NavController,
    navGraphBuilder: NavGraphBuilder,
    transferViewModel: TransferViewModel,
    onSelectContact: (Contact) -> Unit
) {
    navGraphBuilder.composable(route = NavRoute.ContactsList.path) {
        ContactsList(
            navController = navController,
            transferViewModel.getContacts(),
            onSelectContact = onSelectContact
        )
    }
}