package com.wwt.jetflow.welcome

import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.wwt.jetflow.CenteredText
import com.wwt.jetflow.SubScreenLayout
import com.wwt.jetflow.singleLine
import com.wwt.jetflowlibrary.navigation.*

@Composable
fun NavHostScreen() {
    val navController = rememberNavigationController<NavHostDestination>(
        startDestination = NavHostDestination.First,
    )

    NavigationBackHandler(navController)

    NavigationHost(controller = navController) { destination ->
        when (destination) {
            NavHostDestination.First -> FirstScreen(
                toSecondScreenButtonClick = {
                    navController.navigate(NavHostDestination.Second(id = 0))
                },
            )
            is NavHostDestination.Second -> SecondScreen(
                id = destination.id,
                toSecondScreenButtonClick = {
                    navController.navigate(NavHostDestination.Second(id = destination.id + 1))
                },
                toThirdScreenButtonClick = {
                    navController.navigate(NavHostDestination.Third)
                }
            )
            NavHostDestination.Third -> ThirdScreen(
                toForthScreenButtonClick = {
                    navController.navigate(NavHostDestination.Forth())
                }
            )
            is NavHostDestination.Forth -> {
                var resultFromFifth by destination.resultFromFifth
                ForthScreen(
                    resultFromFifth = resultFromFifth,
                    toFifthScreenButtonClick = {
                        navController.navigate(NavHostDestination.Fifth)
                    },
                    onClearResultClick = {
                        resultFromFifth = null
                    }
                )
            }
            is NavHostDestination.Fifth -> {
                var text by rememberSaveable { mutableStateOf("") }
                FifthScreen(
                    text = text,
                    onTextChange = { text = it },
                    backToForthScreenButtonClick = {
                        val previousDestination = navController.backstack.entries.let {
                            it[it.lastIndex - 1].destination
                        }
                        check(previousDestination is AcceptsResultFromFifth)
                        previousDestination.resultFromFifth.value = text
                        navController.pop()
                    },
                    goBackButtonClick = {
                        navController.popUpTo { it == NavHostDestination.First }
                    }
                )
            }
        }
    }
}

@Composable
private fun FirstScreen(
    toSecondScreenButtonClick: () -> Unit,
) = SubScreenLayout(title = "First screen") {

    CenteredText(
        text = """NavHost switches between destinations without any animations.
            Go to Second screen and see how it works.""".singleLine(),
    )

    Button(onClick = toSecondScreenButtonClick) {
        Text("To Second screen")
    }

}

@Composable
private fun SecondScreen(
    id: Int,
    toSecondScreenButtonClick: () -> Unit,
    toThirdScreenButtonClick: () -> Unit
) = SubScreenLayout(title = "Second screen id=$id") {
    CenteredText(
        text = """You can pass any serializable/parcelable data you want. Here you
            can keep opening more Second screens with incrementing 'id' parameter.
            """.singleLine(),
    )

    Button(onClick = toSecondScreenButtonClick) {
        Text("To Second screen + 1")
    }

    CenteredText(
        text = "Also try pressing back. Or just go to the third screen.",
    )

    Button(onClick = toThirdScreenButtonClick) {
        Text("To Third screen")
    }
}

@Composable
private fun ThirdScreen(
    toForthScreenButtonClick: () -> Unit
) = SubScreenLayout(title = "Third screen") {
    CenteredText(
        text = "Now enter some text. This text will be saved while the screen is in the backstack.",
    )

    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(value = text, onValueChange = { text = it })

    CenteredText(
        text = """To test it go to the Forth screen and return back. You can also rotate the 
            screen to test saved state restoration.""".singleLine(),
    )

    Button(onClick = toForthScreenButtonClick) {
        Text("To Forth screen")
    }
}

@Composable
private fun ForthScreen(
    resultFromFifth: String?,
    toFifthScreenButtonClick: () -> Unit,
    onClearResultClick: () -> Unit
) = SubScreenLayout(title = "Forth screen") {

    CenteredText(
        text = "You can also use a separate DialogNavHost for managing dialogs.",
    )


    CenteredText(
        text = "To check out how you can return values from destinations go to the next screen.",
    )

    Button(onClick = toFifthScreenButtonClick) {
        Text("To Fifth screen")
    }

    if (resultFromFifth != null) {
        Text("Result from fifth: $resultFromFifth")

        Button(onClick = onClearResultClick) {
            Text("Clear result")
        }
    }

}

@Composable
private fun FifthScreen(
    text: String,
    onTextChange: (String) -> Unit,
    backToForthScreenButtonClick: () -> Unit,
    goBackButtonClick: () -> Unit,
) = SubScreenLayout(title = "Fifth screen") {

    CenteredText(
        text = "Here you can enter some text and pass it back to the previous screen.",
    )

    OutlinedTextField(value = text, onValueChange = onTextChange)

    Button(onClick = backToForthScreenButtonClick) {
        Text("Return result to Forth screen")
    }

    CenteredText(
        text = """Note: use it carefully. Mutable state increases the complexity of the backstack 
            logic. Sometimes it is more reasonable to have a hoisted data holder.""".singleLine(),
    )

    CenteredText(
        text = """Finally when you are done, you may go back to the very beginning.
            All previous screens will be removed from the backstack.
            """.singleLine(),
    )

    Button(onClick = goBackButtonClick) {
        Text("Go back to First screen")
    }

}
