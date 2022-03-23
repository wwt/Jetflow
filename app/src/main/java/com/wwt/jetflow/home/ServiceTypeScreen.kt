package com.wwt.jetflow.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ServiceTypeScreen(
    email: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val name: String = email.substringBefore("@")
        Text(
            text = "Welcome $name",
            color = Color.Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 14.dp),
            fontSize = 24.sp
        )

        Text(
            text = "Select Service type to place an order",
            modifier = Modifier.padding(top = 14.dp), fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { }) {
            Text(
                "Delivery", fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier
                    .width(150.dp)
            )
        }
        Button(onClick = {}) {
            Text(
                "Pick-up", fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier
                    .width(150.dp)
            )
        }

    }

}