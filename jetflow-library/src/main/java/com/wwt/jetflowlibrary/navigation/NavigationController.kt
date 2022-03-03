package com.wwt.jetflowlibrary.navigation

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController

class NavigationController<T> internal constructor(
    initialEntries: List<ScreenName<T>>
//    initialAction: NavAction = NavAction.Idle

) : Parcelable {
//    constructor(parcel: Parcel) : this() {
//    }

    private val entries = mutableStateOf(initialEntries)
    val backstack = NavigationBackstack(entries)

    override fun describeContents(): Int =0

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }

    companion object CREATOR : Parcelable.Creator<NavigationController<*>> {
        override fun createFromParcel(parcel: Parcel): NavigationController<*>? {
//            val classLoader = nullableClassLoader ?: this::class.java.classLoader
//            val entryMap = hashMapOf<NavId, NavEntry<Any?>>()
            return NavigationController(return NavigationController(initialEntries = List(parcel.readInt()) {
//                val id = parcel.readParcelable<NavId>(classLoader)!!
//                entryMap.getOrPut(id) {
//                    // Here we do the opposite to "writeToParcel" method and read
//                    // the destination value only if its id appears for the first time.
//                    //
//                    // This way we don't duplicate instances with same unique ids.
//                    ScreenName(
////                        id = id,
//                        name = parcel.readValue(classLoader)
//                    )
//                }
                   return null
            },))
        }

        override fun newArray(size: Int): Array<NavigationController<*>?> {
            return arrayOfNulls(size)
        }
    }

}

@Composable
fun getNavigationController() = rememberNavController()

@Composable
fun <T> getNavigationController(startDestination: T) =
    rememberNavController(initialBackstack = listOf(startDestination))

@Composable
fun <T> rememberNavController(initialBackstack: List<T>) = rememberSaveable {
    NavigationControler(initialBackstack = initialBackstack)
}

fun <T> NavigationControler(initialBackstack: List<T>) =
    NavigationController(initialEntries = initialBackstack.map(::screenEntry))
