package com.wwt.jetflowlibrary

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.wwt.jetflow.home.HomeScreen
import com.wwt.jetflow.home.login.LoginScreen
import com.wwt.jetflow.home.login.LoginViewModel
import com.wwt.jetflow.utilis.ResourcesHelper
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
        composeTestRule.onNodeWithText("Enter Email").performTextClearance()

        composeTestRule.onNodeWithText("Login").performClick()

        composeTestRule.onNodeWithText("Enter your Email Id").assertExists()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithInvalidEMail() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val email = composeTestRule.onNodeWithText("Enter Email")
        email.performTextClearance()
        email.performTextInput("test")

        composeTestRule.onNodeWithText("Login").performClick()

        composeTestRule.onNodeWithText("Enter valid Email Id").assertExists()
    }

    @Test
    fun validateEmailWhenLoginButtonClickedWithValidEMail() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        composeTestRule.onNodeWithText("Enter Email").performTextInput("test@test.com")

        composeTestRule.onNodeWithText("Login").performClick()

        composeTestRule.onNodeWithText("Enter your Email Id").assertDoesNotExist()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithoutPassword() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val email = composeTestRule.onNodeWithText("Enter Email")
        email.performTextClearance()
        email.performTextInput("test@test.com")
        composeTestRule.onNodeWithText("Enter Password").performTextClearance()

        composeTestRule.onNodeWithText("Login").performClick()

        composeTestRule.onNodeWithText("Enter your Password").assertIsDisplayed()
    }

    @Test
    fun shouldThrowErrorWhenLoginButtonClickedWithInvalidPasswordLength() {
        composeTestRule.setContent {
            LoginScreen(loginButtonClick = { })
        }
        val email = composeTestRule.onNodeWithText("Enter Email")
        email.performTextClearance()
        email.performTextInput("test@test.com")
        val password = composeTestRule.onNodeWithText("Enter Password")
        password.performTextClearance()
        password.performTextInput("12345")

        composeTestRule.onNodeWithText("Login").performClick()

        password.assertIsDisplayed()
        Thread.sleep(5000)
        composeTestRule.onNodeWithText("Password length should be more than 6 characters")
            .assertIsDisplayed()
    }

    @Test
    fun shouldNavigateToServiceTypeScreenWhenLoginButtonClickedWithValidDetails() {
        composeTestRule.setContent {
            HomeScreen()
        }
        composeTestRule.onNodeWithText("Enter Email").performTextInput("test@wwt.com")
        composeTestRule.onNodeWithText("Enter Password").performTextInput("12345678")

        composeTestRule.onNodeWithText("Login").performClick()

        composeTestRule.onNodeWithText("Welcome test").assertIsDisplayed()
    }
}