package com.wwt.jetflow

import androidx.compose.runtime.Composable
import com.wwt.jetflow.welcome.WelcomeScreen
import com.wwt.jetflow.welcome.WelcomeViewModel
import org.koin.androidx.compose.inject

@Composable
fun JetflowApp() {
    val welcomeViewModel: WelcomeViewModel by inject()
    WelcomeScreen(
        state = welcomeViewModel.viewState.value,
        effectFlow = welcomeViewModel.effect,
        onEventSent = { event -> welcomeViewModel.setEvent(event) })
}