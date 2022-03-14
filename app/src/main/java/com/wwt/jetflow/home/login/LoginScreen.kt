package com.wwt.jetflow.home.login

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    OutlinedTextField(
        value = state.email,
        onValueChange = {
            Log.v("value", "value: $it")
            if (emailErrorState.value) {
                emailErrorState.value = false
            }
            loginViewModel.setEmail(it)
        },
        isError = emailErrorState.value,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Enter Email")
        },
    )

    val passwordVisibility = remember { mutableStateOf(true) }
    OutlinedTextField(
        value = state.password,
        onValueChange = {
            if (passwordErrorState.value) {
                passwordErrorState.value = false
            }
            loginViewModel.setPassword(it)
        },
        isError = passwordErrorState.value,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Enter Password")
        },
        visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
    )

    Button(onClick = { loginButtonClick(state.email) }) {
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