package com.wwt.jetflowlibrary.navigation

import androidx.compose.runtime.Stable

@Stable
class NavigationEntry<T> internal constructor(

    val id: NavigationId = NavigationId(),

    val destination: T
) {

    override fun toString(): String {
        return "NavigationEntry(id=$id, destination=$destination)"
    }

}

fun <T> navigationEntry(destination: T) = NavigationEntry(destination = destination)