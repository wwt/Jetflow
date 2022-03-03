package com.wwt.jetflow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wwt.jetflow.NavigationKeys.Arg.D_SCREEN_ARGUMENT
import com.wwt.jetflow.NavigationKeys.Route.A_SCREEN
import com.wwt.jetflow.NavigationKeys.Route.B_SCREEN
import com.wwt.jetflow.NavigationKeys.Route.C_SCREEN
import com.wwt.jetflow.NavigationKeys.Route.D_SCREEN
import com.wwt.jetflow.welcome.WelcomeScreen
import com.wwt.jetflow.welcome.WelcomeViewModel
import com.wwt.jetflowlibrary.navigation.GetNavHost
import com.wwt.jetflowlibrary.navigation.GetNavigationHost
import com.wwt.jetflowlibrary.navigation.getNavigationController
import org.koin.androidx.compose.inject

@Composable
fun JetflowApp() {
    val welcomeViewModel: WelcomeViewModel by inject()
//    WelcomeScreen(
//        state = welcomeViewModel.viewState.value,
//        effectFlow = welcomeViewModel.effect,
//        onEventSent = { event -> welcomeViewModel.setEvent(event) })

    Navigation()
}

enum class MainDestination {
    AScreen,
    BScreen
}

@Composable
fun Navigation(){
    val navController = getNavigationController(A_SCREEN)  //rememberNavController()

    GetNavigationHost(controller = navController){ destination ->
        when (destination) {
//            A_SCREEN -> AScreen(navController = navController)
//            B_SCREEN -> BScreen(navController = navController)
//            C_SCREEN -> CScreen(navController = navController)
//            D_SCREEN -> DScreen(navController = navController)
            MainDestination.AScreen -> AScreen(navController)
        }
    }

//    NavHost(navController = navController,
//        startDestination = A_SCREEN) {
//        composable(A_SCREEN) {
//            AScreen(navController = navController)
//        }
//
//        // B Screen
//        composable(B_SCREEN) {
//            BScreen(navController = navController)
//        }
//        // C Screen
//        composable(C_SCREEN) {
//            CScreen(navController = navController)
//        }
//
//        // D Screen
//        composable(D_SCREEN, arguments = listOf(navArgument(D_SCREEN_ARGUMENT){
//            type = NavType.StringType
//        })) {
//            DScreen(navController = navController)
//        }
//    }

}

@Composable
fun AScreen(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            Text(text = "A screen")
            OutlinedButton(onClick = {
                navController.navigate(B_SCREEN)
            }) {
                Text(text = "Next Screen", color= Color.Blue)
            }
        }
    }
}

@Composable
fun BScreen(navController: NavController) {
    Column() {
        Text(text = "Welcome to B screen")
        OutlinedButton(onClick = {
            navController.navigate(C_SCREEN)
        }) {
            Text(text = "Next Screen", color= Color.Blue)
        }
    }
}

@Composable
fun CScreen(navController: NavController) {
    Column() {
        Text(text = "Welcome to C screen")
        OutlinedButton(onClick = {
            navController.navigate(A_SCREEN)
        }) {
            Text(text = "Next Screen", color= Color.Blue)
        }
    }
}

@Composable
fun DScreen(navController: NavController) {
    Text(text = "D screen", color= Color.Blue)
}

object NavigationKeys {

    object Arg {
        const val D_SCREEN_ARGUMENT = "argument"
    }

    object Route {
        const val A_SCREEN = "a_screen"
        const val B_SCREEN = "b_screen"
        const val C_SCREEN = "c_screen"
        const val D_SCREEN = "d_screen"
    }

}