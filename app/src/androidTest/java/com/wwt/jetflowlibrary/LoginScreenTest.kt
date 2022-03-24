package com.wwt.jetflowlibrary

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wwt.jetflow.home.login.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

//@RunWith(MockitoJUnitRunner::class)
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val initRule: MockitoRule = MockitoJUnit.rule()

//    @Mock
//    val loginButtonClick: (String) -> Unit = TODO()

    @Test
    fun assertLoginTitle() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithEmptyEMail() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val login = composeTestRule.onNodeWithText("Login")
        login.assertIsDisplayed()
        login.performClick()
        composeTestRule.onNodeWithText("Enter Email Id").assertExists()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithInvalidEMail() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val login = composeTestRule.onNodeWithText("Login")
        login.assertIsDisplayed()

        composeTestRule.onNodeWithText("Enter Email").performTextInput("test")
        login.performClick()

        composeTestRule.onNodeWithText("Enter valid Email Id").assertExists()
    }

    @Test
    fun validateEmailWhenLoginButtonClickedWithValidEMail() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val login = composeTestRule.onNodeWithText("Login")
        login.assertIsDisplayed()

        composeTestRule.onNodeWithText("Enter Email").performTextInput("test@wwt.com")
        login.performClick()

        composeTestRule.onNodeWithText("Enter Email Id").assertDoesNotExist()
//        composeTestRule.onNodeWithText("Enter valid Email Id").assertDoesNotExist()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithoutPassword() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val login = composeTestRule.onNodeWithText("Login")
        composeTestRule.onNodeWithText("Enter Email").performTextInput("test@wwt.com")

        login.performClick()

        composeTestRule.onNodeWithText("Enter Password").assertExists()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithInvalidPasswordLength() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val login = composeTestRule.onNodeWithText("Login")
        composeTestRule.onNodeWithText("Enter Email").performTextInput("test@wwt.com")
        composeTestRule.onNodeWithText("Enter Password").performTextInput("12345")

        login.performClick()

        composeTestRule.onNodeWithText("Password length should be more than 6 characters").assertExists()
    }
}