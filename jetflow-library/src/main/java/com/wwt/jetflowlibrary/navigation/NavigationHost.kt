package com.wwt.jetflowlibrary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key

@Composable
fun <T> NavigationHost(
    controller: NavigationController<T>,
    emptyBackstackPlaceholder: @Composable () -> Unit = {},
    contentSelector: @Composable (T) -> Unit
) = NavigationHost(
    backstack = controller.backstack,
    emptyBackstackPlaceholder = emptyBackstackPlaceholder,
    contentSelector = contentSelector
)

@Composable
fun <T> NavigationHost(
    backstack: NavigationBackstack<T>,
    emptyBackstackPlaceholder: @Composable () -> Unit = {},
    contentSelector: @Composable (T) -> Unit
) = BaseNavigationHost(backstack) { lastComponentEntry ->
    key(lastComponentEntry?.id) {
        if (lastComponentEntry != null) {
            lastComponentEntry.ComponentProvider {
                contentSelector(lastComponentEntry.destination)
            }
        } else {
            emptyBackstackPlaceholder()
        }
    }
    lastComponentEntry
}
