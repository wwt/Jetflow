package com.wwt.jetflowlibrary.navigation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue

@Stable
class NavigationBackstack<T> internal constructor(
    entriesState: State<List<NavigationEntry<T>>>,
    actionState: State<NavigationAction>
) {

    val entries: List<NavigationEntry<T>> by entriesState

    val action: NavigationAction by actionState
}