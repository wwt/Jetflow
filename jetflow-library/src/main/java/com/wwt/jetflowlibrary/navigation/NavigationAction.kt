package com.wwt.jetflowlibrary.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


abstract class NavigationAction : Parcelable {

    @Parcelize
    object Idle : NavigationAction() {
        override fun toString() = this::class.simpleName!!
    }

    @Parcelize
    object Navigate : NavigationAction() {
        override fun toString() = this::class.simpleName!!
    }

    @Parcelize
    object Replace : NavigationAction() {
        override fun toString() = this::class.simpleName!!
    }

    @Parcelize
    object Pop : NavigationAction() {
        override fun toString() = this::class.simpleName!!
    }

}
