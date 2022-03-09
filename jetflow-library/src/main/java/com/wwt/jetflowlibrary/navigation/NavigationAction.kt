package com.wwt.jetflowlibrary.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


abstract class NavigationAction : Parcelable {

    @Parcelize
    object Idle : NavigationAction() {}

    @Parcelize
    object Navigate : NavigationAction() {}

    @Parcelize
    object Replace : NavigationAction() {}

    @Parcelize
    object Pop : NavigationAction() {}

}
