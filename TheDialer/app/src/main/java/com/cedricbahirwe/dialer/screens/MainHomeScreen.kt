package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.cedricbahirwe.dialer.common.DefaultButton
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.navigation.NavRoute

@Composable
fun MainHomeScreen (navController: NavController){

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultButton(
                text = context.getString(R.string.airtime_purchase),
                onClick = { navController.navigate(NavRoute.AirtimePurchase.path) }
            )

            DefaultButton(
                text = context.getString(R.string.history_screen),
                onClick = { navController.navigate(NavRoute.History.path) }
            )

            DefaultButton(
                text = context.getString(R.string.pin_view),
                onClick = { navController.navigate(NavRoute.PinView.path) }
            )

            DefaultButton(
                text = context.getString(R.string.send_screen),
                onClick = { navController.navigate(NavRoute.Send.path) }
            )

            DefaultButton(
                text = context.getString(R.string.home_screen),
                onClick = { navController.navigate(NavRoute.HomeScreen.path) }
            )
        }
    }
}