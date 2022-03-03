package com.wwt.jetflowlibrary.navigation

class ScreenName<T> internal constructor(
    val name: T
) {
    override fun toString(): String {
        return "ScreenName(name=$name)"
    }
}

fun <T> screenEntry(destination: T) = ScreenName(name = destination)

