package com.cedricbahirwe.dialer

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.runner.AndroidJUnitRunner
import com.cedricbahirwe.dialer.screens.DashBoardContainer
import com.cedricbahirwe.dialer.screens.TransferView
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.TransferViewModelFactory
import org.junit.Rule
import org.junit.Test

class DialerUITests : AndroidJUnitRunner() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDialUSSD() {
        composeTestRule.setContent {
            DialerTheme {
                TransferView(
                    rememberNavController(),
                    viewModel(
                        factory = TransferViewModelFactory(
                            LocalContext.current
                        )
                    ),
                    openContactList = {}
                )
            }
        }
        val dialButton = composeTestRule.onNodeWithText("Dial USSD")
        dialButton.assertIsNotEnabled()
        composeTestRule.onNodeWithText("Amount").performTextInput("1000")
        composeTestRule.onNodeWithText("Merchant Code").performTextInput("12345")
        dialButton.assertIsEnabled()
    }

    @Test
    fun testHomeComponentDisplay() {
        testHomeMenuItemsDisplay()
    }

    @Test
    fun testHomeMenuItemsDisplay() {
        loadHomeContent()
        composeTestRule.onNodeWithText("Buy airtime").assertExists()
        composeTestRule.onNodeWithText("Transfer/Pay").assertExists()
        composeTestRule.onNodeWithText("History").assertExists()
        composeTestRule.onNodeWithText("My Space").assertExists()
    }

    private fun loadHomeContent() {
        composeTestRule.setContent {
            DialerTheme {
                DashBoardContainer(navController = rememberNavController())
            }
        }
    }
}
