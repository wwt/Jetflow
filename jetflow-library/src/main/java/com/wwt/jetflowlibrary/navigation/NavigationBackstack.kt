package com.wwt.jetflowlibrary.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue

class NavigationBackstack<T> internal constructor(
    entriesState: State<List<ScreenName<T>>>
) {
    /**
     * The list of current entries in the backstack. The last item in this list is the item that
     * will be displayed by [NavHost].
     *
     * May become empty if you pop all the items off the backstack.
     */
    val entries: List<ScreenName<T>> by entriesState

    /**
     * The action of the last [NavController.setNewBackstackEntries] call.
     *
     * The initial value of every new instance of [NavController] is [NavAction.Idle].
     */
//    val action: NavAction by actionState

    override fun toString(): String {
        return "NavigationBackstack(entries=$entries)"
    }

}
