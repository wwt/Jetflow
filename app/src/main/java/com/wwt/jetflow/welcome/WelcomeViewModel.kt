package com.wwt.jetflow.welcome

import androidx.lifecycle.viewModelScope
import com.wwt.jetflow.base.BaseViewModel
import kotlinx.coroutines.launch


class WelcomeViewModel :
    BaseViewModel<WelcomeScreenContract.Event,
            WelcomeScreenContract.State,
            WelcomeScreenContract.Effect>() {


    init {
        viewModelScope.launch {
            setState {
                copy(welcomeText = "Welcome to Jetflow")
            }
        }
    }

    override fun setInitialState(): WelcomeScreenContract.State =
        WelcomeScreenContract.State(welcomeText = "")

    override fun handleEvents(event: WelcomeScreenContract.Event) {
        TODO("Not yet implemented")
    }
}