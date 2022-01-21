package com.wwt.jetflow.welcome

import com.wwt.jetflow.base.ViewEvent
import com.wwt.jetflow.base.ViewSideEffect
import com.wwt.jetflow.base.ViewState


class WelcomeScreenContract {
        sealed class Event : ViewEvent {
    }

    data class State(
        val welcomeText: String =""
    ) : ViewState

    sealed class Effect : ViewSideEffect {
    }
}