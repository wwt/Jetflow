package com.wwt.jetflow.home.login

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.wwt.jetflow.R
import com.wwt.jetflow.base.BaseViewModel
import com.wwt.jetflow.utilis.ResourcesHelper
import kotlinx.coroutines.launch

class LoginViewModel(private val resourcesHelper: ResourcesHelper) :
    BaseViewModel<LoginScreenContract.Event,
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

    val user = MediatorLiveData<LoginStatus>()

    private fun isEmailValid(emailAddress: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }

    fun login(email: String, password: String) {
        if (email.isEmpty()) {
            user.postValue(LoginStatus.emailError(resourcesHelper.getStringResource(R.string.error_email_empty)))
        } else if (!isEmailValid(email)) {
            user.postValue(LoginStatus.emailError(resourcesHelper.getStringResource(R.string.error_email)))
        } else if (password.isEmpty()) {
            user.postValue(LoginStatus.passwordError(resourcesHelper.getStringResource(R.string.error_password_empty)))
        } else if (password.length <= 6) {
            user.postValue(LoginStatus.passwordError(resourcesHelper.getStringResource(R.string.error_password_length)))
        } else {
            user.postValue(LoginStatus.success())
        }
    }
}