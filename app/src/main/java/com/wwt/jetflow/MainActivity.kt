package com.wwt.jetflow

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wwt.jetflow.base.BaseActivity
import com.wwt.jetflow.home.HomeScreen
import com.wwt.jetflow.ui.theme.JetflowTheme
import com.wwt.jetflow.welcome.NavHostScreen

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetflowTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    NavHostScreen()
                    HomeScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetflowTheme {
        NavHostScreen()
    }
}