package com.wwt.jetflowlibrary.navigation

import androidx.compose.runtime.Stable

@Stable
class NavigationEntry<T> internal constructor(

    val id: NavigationId = NavigationId(),

    val destination: T
){}

fun <T> navigationEntry(destination: T) = NavigationEntry(destination = destination)