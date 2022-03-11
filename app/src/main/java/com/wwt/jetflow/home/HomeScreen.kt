package com.wwt.jetflow.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.wwt.jetflow.welcome.NavHostDestination
import com.wwt.jetflowlibrary.navigation.NavigationBackHandler
import com.wwt.jetflowlibrary.navigation.NavigationHost
import com.wwt.jetflowlibrary.navigation.navigate
import com.wwt.jetflowlibrary.navigation.rememberNavigationController

@Composable
fun HomeScreen() {
    val navController = rememberNavigationController<NavHostDestination>(
        startDestination = NavHostDestination.Login,
    )

    NavigationBackHandler(navController)
    NavigationHost(controller = navController) { destination ->
        when (destination) {

            NavHostDestination.Login -> {
                var text by rememberSaveable { mutableStateOf("") }
                LoginScreen(
                    userEmail = text,
                    onTextChange = { text = it },
                    loginButtonClick = {
                        navController.navigate(NavHostDestination.ServiceType(email = text))
                    },
                )
            }
            is NavHostDestination.ServiceType ->
                ServiceTypeScreen(
                    email = destination.email,
                    toSecondScreenButtonClick = {
//                        navController.navigate(NavHostDestination.ServiceType(id = destination.id + 1))
                    },
//                toThirdScreenButtonClick = {
//                    navController.navigate(NavHostDestination.Third)
//                }
                )
        }
    }
}