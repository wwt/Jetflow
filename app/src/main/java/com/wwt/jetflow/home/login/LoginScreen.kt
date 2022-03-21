package com.wwt.jetflow.home.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wwt.jetflow.SubScreenLayout
import org.koin.androidx.compose.viewModel

@Composable
fun LoginScreen(
    loginButtonClick: (String) -> Unit
) = SubScreenLayout(title = "LOGIN ") {

    val loginViewModel: LoginViewModel by viewModel()
    val state: LoginScreenContract.State = loginViewModel.viewState.value

    val user= loginViewModel.user

    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val emailErrorText = remember { mutableStateOf("") }
    val passwordErrorText = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    when (user.status) {
        Status.SUCCESS -> {
            emailErrorState.value = false
            passwordErrorState.value = false
            emailErrorText.value = ""
            passwordErrorText.value = ""
            loginViewModel.user= LoginStatus.empty()
            loginButtonClick(state.email)
        }
        Status.EMAIL_ERROR -> {
            emailErrorState.value = true
            emailErrorText.value = user.message.toString()
        }
        Status.PASSWORD_ERROR -> {
            emailErrorState.value = false
            emailErrorText.value = ""
            passwordErrorState.value = true
            passwordErrorText.value = user.message.toString()
        }
        else -> {
            emailErrorState.value = false
            emailErrorText.value = ""
            passwordErrorState.value = false
            passwordErrorText.value = ""

        }
    }

    Column {
        OutlinedTextField(
            value = state.email,
            onValueChange = {
                emailErrorState.value = false
                loginViewModel.setEmail(it)
            },
            isError = emailErrorState.value,
            label = {
                Text(text = "Enter Email")
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
        )
        if (emailErrorState.value) {
            Text(
                text = emailErrorText.value,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
    Column {
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = state.password,
            onValueChange = {
                passwordErrorState.value = false
                loginViewModel.setPassword(it)
            },
            singleLine = true,
            isError = passwordErrorState.value,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Enter Password")
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (passwordErrorState.value) {
            Text(
                text = passwordErrorText.value,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
    Button(onClick = {
        loginViewModel.login(state.email, state.password)
    }) {
        Text(
            "Login", fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier
                .width(150.dp)
        )
    }
}

@Composable
@Preview
private fun LoginPreview() {
//    LoginScreen {}
}