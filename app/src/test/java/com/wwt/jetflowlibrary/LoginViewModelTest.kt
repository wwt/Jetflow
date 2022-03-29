package com.wwt.jetflowlibrary

import com.wwt.jetflow.R
import com.wwt.jetflow.home.login.LoginViewModel
import com.wwt.jetflow.home.login.Status
import com.wwt.jetflow.utilis.ResourcesHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @Mock
    private lateinit var resourcesHelper: ResourcesHelper

    private lateinit var testObject: LoginViewModel

    @Test
    fun `login returns email error status when email is empty or invalid`() {

        `when`(resourcesHelper.getStringResource(R.string.error_email_empty))
            .thenReturn("Enter your Email Id")

        testObject.login("", "")

        Assert.assertEquals(Status.EMAIL_ERROR, testObject.user.status)

    }
}