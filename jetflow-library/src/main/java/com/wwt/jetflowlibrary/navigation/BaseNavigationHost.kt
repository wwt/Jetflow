package com.wwt.jetflowlibrary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key

@Composable
internal fun <T> BaseNavigationHost(
    backstack: NavigationBackstack<T>,
    entryTransition: @Composable (NavigationComponentEntry<T>?) -> NavigationComponentEntry<T>?
) {

    val componentHolder = rememberNavigationComponentHolder(backstack)

    val currentComponentEntry = key(componentHolder.id) {
        entryTransition(componentHolder.lastComponentEntry)
    }

    DisposableEffect(componentHolder, currentComponentEntry) {
        onDispose {
            // should be called only in onDispose, because it affects lifecycle events
            componentHolder.onTransitionFinish()
        }
    }

    DisposableEffect(componentHolder) {
        componentHolder.onCreate()
        onDispose {
            componentHolder.onDispose()
        }
    }
}