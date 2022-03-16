package com.wwt.jetflowlibrary.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun <T> NavigationBackHandler(
    navigationController: NavigationController<T>
) {
    BackHandler(enabled = navigationController.backstack.entries.size > 1) {
        navigationController.pop()
    }
}