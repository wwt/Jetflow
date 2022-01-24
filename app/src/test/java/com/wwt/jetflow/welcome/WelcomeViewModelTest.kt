package com.wwt.jetflow.welcome

import org.junit.Assert
import org.junit.Test

class WelcomeViewModelTest {
    private val testObject: WelcomeViewModel = WelcomeViewModel()

    @Test
    fun `on init state welcome text should be load welcome text should be Welcome to Himalaya Creamery`(){
        Assert.assertEquals(testObject.viewState.value.welcomeText, "Welcome to Himalaya Creamery")
    }
}