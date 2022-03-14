package com.wwt.jetflow.home.login

import com.wwt.jetflow.base.ViewEvent
import com.wwt.jetflow.base.ViewSideEffect
import com.wwt.jetflow.base.ViewState

class LoginScreenContract {
    sealed class Event : ViewEvent {
    }

    data class State(
        var email: String ="",
        var password: String =""
    ) : ViewState

    sealed class Effect : ViewSideEffect {
    }
}