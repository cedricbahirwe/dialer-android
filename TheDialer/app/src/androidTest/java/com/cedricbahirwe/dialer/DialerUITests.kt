package com.cedricbahirwe.dialer

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.runner.AndroidJUnitRunner
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import org.junit.Rule
import org.junit.Test

class DialerUITests : AndroidJUnitRunner() {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dial_ussd() {
        composeTestRule.setContent {
            DialerTheme {
                FieldsContainer()
            }
        }
        composeTestRule.onNodeWithText("Amount").performTextInput("1000")
        composeTestRule.onNodeWithText("Phone Number").performTextInput("0782628511")
        composeTestRule.onNodeWithText("Valid Input").assertExists(
            "No node with this text was found."
        )
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

    @Test
    fun testDialButtonDisplay() {
        loadHomeContent()
        composeTestRule.onNodeWithText("Quick Dial").assertExists()
        composeTestRule.onNodeWithText("Quick Dial").assertIsNotEnabled()
    }

    private fun loadHomeContent() {
        composeTestRule.setContent {
            DialerTheme {
                DashBoardContainer()
            }
        }
    }
}
