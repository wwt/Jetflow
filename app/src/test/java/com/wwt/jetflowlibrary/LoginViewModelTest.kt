package com.wwt.jetflowlibrary

import android.util.Patterns
import androidx.core.util.PatternsCompat
import androidx.core.util.PatternsCompat.EMAIL_ADDRESS
import com.wwt.jetflow.R
import com.wwt.jetflow.home.login.LoginViewModel
import com.wwt.jetflow.home.login.Status
import com.wwt.jetflow.utilis.ResourcesHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.regex.Matcher
import java.util.regex.Pattern

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var resourcesHelper: ResourcesHelper

    private lateinit var testObject: LoginViewModel


    private fun getTestObject(): LoginViewModel {
        return LoginViewModel(
            resourcesHelper
        )
    }

    @Test
    fun `login returns email error status when email is empty`() {

        `when`(resourcesHelper.getStringResource(R.string.error_email_empty))
            .thenReturn("Enter your Email Id")
        testObject = getTestObject()

        testObject.login("", "")

        Assert.assertEquals(Status.EMAIL_ERROR, testObject.user.status)
        Assert.assertEquals("Enter your Email Id", testObject.user.message)
    }

    @Test
    fun `login returns invalid email error status when email is invalid pattern`() {
        testObject = getTestObject()

        val email="test"
        `when`(resourcesHelper.getStringResource(R.string.error_email))
            .thenReturn("Enter valid Email Id")

//        val patterns: PatternsCompat = mock(PatternsCompat::class.java)
//        val pattern: Pattern = mock(Pattern::class.java)
//        val matcher: Matcher = mock(Matcher::class.java)
//
////        Mockito.mockStatic(PatternsCompat::class.java).use { mocked ->
////            mocked.`when`<Pattern> { EMAIL_ADDRESS }
////                .thenReturn(pattern)
////        }
////        `when`(Patterns.EMAIL_ADDRESS.matcher(email).matches()).thenReturn(false)
////        `when`(PatternsCompat.EMAIL_ADDRESS).thenReturn(pattern)
//        `when`(pattern.matcher(email)).thenReturn(matcher)
//        `when`(matcher.matches()).thenReturn(false)



        `when`(testObject.isEmailValid(email)).thenReturn(false)





        testObject.login(email, "")

        Assert.assertEquals(Status.EMAIL_ERROR, testObject.user.status)
        Assert.assertEquals("Enter valid Email Id", testObject.user.message)
    }

    @Test
    fun `login returns password error status when password is empty`() {
        val message = "Enter your Password"
        `when`(resourcesHelper.getStringResource(R.string.error_password_empty))
            .thenReturn(message)
        testObject = getTestObject()

        testObject.login("test@test.com", "")

        Assert.assertEquals(Status.PASSWORD_ERROR, testObject.user.status)
        Assert.assertEquals(message, testObject.user.message)
    }

    @Test
    fun `login returns password length error status when password length is less than 6`() {
        val message = "Password length should be more than 6 characters"
        `when`(resourcesHelper.getStringResource(R.string.error_password_length))
            .thenReturn(message)
        testObject = getTestObject()

        testObject.login("test@test.com", "12345")

        Assert.assertEquals(Status.PASSWORD_ERROR, testObject.user.status)
        Assert.assertEquals(message, testObject.user.message)
    }

    @Test
    fun `login returns success status when entered valid details`() {
        testObject = getTestObject()

        testObject.login("test@test.com", "123456789")

        Assert.assertEquals(Status.SUCCESS, testObject.user.status)
    }
}