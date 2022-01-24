package com.wwt.jetflow.welcome

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.wwt.jetflow.JetflowApp
import com.wwt.jetflow.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WelcomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetflowApp()
        }
    }

    @Test
    fun displayWelcomeMessage() {
        // Check app launches at the correct destination
        composeTestRule.onNodeWithText("Welcome to Himalaya Creamery").assertIsDisplayed()
    }
}