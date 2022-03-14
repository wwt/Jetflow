package com.wwt.jetflow.home.login

import androidx.lifecycle.viewModelScope
import com.wwt.jetflow.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<LoginScreenContract.Event,
        LoginScreenContract.State,
        LoginScreenContract.Effect>() {

    init {
        viewModelScope.launch {
            setState {
                copy(email = this.email, password = this.password)
            }
        }
    }

    override fun setInitialState(): LoginScreenContract.State =
        LoginScreenContract.State(email = "", password = "")

    override fun handleEvents(event: LoginScreenContract.Event) {

    }

    fun setEmail(emailText: String) {
        setState {
            copy(email = emailText)
        }
    }

    fun setPassword(passwordText: String) {
        setState {
            copy(password = passwordText)
        }
    }
}