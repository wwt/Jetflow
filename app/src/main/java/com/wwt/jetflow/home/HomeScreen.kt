package com.wwt.jetflow.home

import androidx.compose.runtime.Composable
import com.wwt.jetflow.home.login.LoginScreen
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
                LoginScreen(
                    loginButtonClick = { emailText ->
                        navController.navigate(NavHostDestination.ServiceType(email = emailText))
                    },
                )
            }
            is NavHostDestination.ServiceType ->
                ServiceTypeScreen(
                    email = destination.email,
                    toSecondScreenButtonClick = {
//                        navController.navigate(NavHostDestination.ServiceType(id = destination.id + 1))
                    },
                )
        }
    }
}