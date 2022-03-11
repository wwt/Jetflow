package com.wwt.jetflow.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.wwt.jetflow.SubScreenLayout

@Composable
fun LoginScreen(
    userEmail: String,
    onTextChange: (String) -> Unit,
    loginButtonClick: () -> Unit,
) = SubScreenLayout(title = "Login screen") {
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    OutlinedTextField(
        value = userEmail,
        onValueChange = onTextChange,
        isError = emailErrorState.value,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Enter Email")
        },
    )

    val passwordVisibility = remember { mutableStateOf(true) }
    OutlinedTextField(
        value = password.value,
        onValueChange = {
            if (passwordErrorState.value) {
                passwordErrorState.value = false
            }
            password.value = it
        },
        isError = passwordErrorState.value,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Enter Password")
        },
        visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
    )

    Button(onClick = loginButtonClick) {

        Text("Login")
    }

}

@Composable
@Preview
private fun LoginPreview(){
//    LoginScreen {}
}