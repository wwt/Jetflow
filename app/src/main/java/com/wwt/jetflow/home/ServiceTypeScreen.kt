package com.wwt.jetflow.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ServiceTypeScreen(
    email: String,
    toSecondScreenButtonClick: () -> Unit
//    toThirdScreenButtonClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 32.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(text = "Welcome ${email}", modifier = Modifier.padding(top = 14.dp))

        Row {
            RadioButton(selected = false, onClick = { /*TODO*/ })
            Text(text = "Delivery", modifier = Modifier.padding(top = 14.dp))
        }

        Row {
            RadioButton(selected = false, onClick = { /*TODO*/ })
            Text(text = "Pick-up", modifier = Modifier.padding(top = 14.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = toSecondScreenButtonClick, ) {
                Text("Continue")
            }
        }

    }

//    Button(onClick = toThirdScreenButtonClick) {
//        Text("To Third screen")
//    }
}
