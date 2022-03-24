package com.wwt.jetflowlibrary

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wwt.jetflow.home.ServiceTypeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ServiceTypeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun assertScreenUIFileds() {
        composeTestRule.setContent {
            ServiceTypeScreen("test@wwt.com")
        }
        composeTestRule.onNodeWithText("Welcome test").assertExists()
        composeTestRule.onNodeWithText("Select Service type to place an order").assertExists()
        composeTestRule.onNodeWithText("Delivery").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pick-up").assertExists().assertIsDisplayed()
    }
}