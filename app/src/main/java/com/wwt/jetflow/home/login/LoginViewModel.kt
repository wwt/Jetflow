package com.wwt.jetflow.home.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.util.PatternsCompat
import androidx.lifecycle.viewModelScope
import com.wwt.jetflow.R
import com.wwt.jetflow.base.BaseViewModel
import com.wwt.jetflow.utilis.ResourcesHelper
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

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

    var user: LoginStatus by mutableStateOf(LoginStatus.empty())

    fun isEmailValid(emailAddress: String): Boolean {
//        val pattern: Pattern = Pattern.compile(Patterns.EMAIL_ADDRESS.toString())
//        val matcher: Matcher = pattern.matcher(emailAddress)
//        return matcher.matches()
        return PatternsCompat.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }

    fun login(email: String, password: String) {
        if (email.isEmpty()) {
            user =
                LoginStatus.emailError(resourcesHelper.getStringResource(R.string.error_email_empty))
        } else if (!isEmailValid(email)) {
            user = LoginStatus.emailError(resourcesHelper.getStringResource(R.string.error_email))
        } else if (password.isEmpty()) {
            user =
                LoginStatus.passwordError(resourcesHelper.getStringResource(R.string.error_password_empty))
        } else if (password.length <= 6) {
            user =
                LoginStatus.passwordError(resourcesHelper.getStringResource(R.string.error_password_length))
        } else {
            user = LoginStatus.success()
        }
    }
}