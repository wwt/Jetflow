package com.wwt.jetflow.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow

@Composable
fun WelcomeScreen(
    state: WelcomeScreenContract.State,
    effectFlow: Flow<WelcomeScreenContract.Effect>?,
    onEventSent: (event: WelcomeScreenContract.Event) -> Unit
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = state.welcomeText)
    }
}