package com.wwt.jetflowlibrary

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.wwt.jetflow.home.HomeScreen
import com.wwt.jetflow.home.login.LoginScreen
import com.wwt.jetflow.home.login.LoginViewModel
import com.wwt.jetflow.home.login.Status
import com.wwt.jetflow.utilis.ResourcesHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule


@RunWith(MockitoJUnitRunner::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val initRule: MockitoRule = MockitoJUnit.rule()

    lateinit var mainViewModel: LoginViewModel
    lateinit var resourceHelper: ResourcesHelper

    @Mock
    lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        resourceHelper = ResourcesHelper(context)
        mainViewModel = LoginViewModel(resourceHelper)
    }

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
        composeTestRule.onNodeWithText("Enter your Email Id").assertExists()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithInvalidEMail() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val login = composeTestRule.onNodeWithText("Login")
        login.assertIsDisplayed()

        val email = composeTestRule.onNodeWithText("Enter Email")
        email.performTextClearance()
        email.performTextInput("test")
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

        composeTestRule.onNodeWithText("Enter Email").performTextInput("test@test.com")
        login.performClick()

        composeTestRule.onNodeWithText("Enter your Email Id").assertDoesNotExist()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithoutPassword() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val login = composeTestRule.onNodeWithText("Login")
        composeTestRule.onNodeWithText("Enter Email").performTextInput("test@test.com")

        login.performClick()

        composeTestRule.onNodeWithText("Enter your Password").assertIsDisplayed()//.assertExists()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithInvalidPasswordLength() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val login = composeTestRule.onNodeWithText("Login")
        val email = composeTestRule.onNodeWithText("Enter Email")
        email.performTextClearance()
        email.performTextInput("test@test.com")
        composeTestRule.onNodeWithText("Enter Password").performTextInput("12345")

        login.performClick()

        composeTestRule.onNodeWithText("Enter Password").assertIsDisplayed()
        Thread.sleep(5000)
        composeTestRule.onNodeWithText("Password length should be more than 6 characters")
            .assertIsDisplayed()//.assertExists()
    }

    @Test
    fun shouldNavigateToServiceTypeScreenWhenLoginButtonClickedWithValidDetails() {
        composeTestRule.setContent {
            HomeScreen()
        }
        val login = composeTestRule.onNodeWithText("Login")
        composeTestRule.onNodeWithText("Enter Email").performTextInput("test@wwt.com")

        composeTestRule.onNodeWithText("Enter Password").performTextInput("12345678")

        login.performClick()

        composeTestRule.onNodeWithText("Welcome test").assertIsDisplayed()

    }
}