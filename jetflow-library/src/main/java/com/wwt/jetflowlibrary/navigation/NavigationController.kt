package com.wwt.jetflowlibrary.navigation

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.MainThread
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadOnlyProperty

@Composable
fun <T> rememberNavigationController(initialBackstack: List<T>) = rememberSaveable {
    navigationController(initialBackstack = initialBackstack)
}

@Composable
fun <T> rememberNavigationController(startDestination: T) =
    rememberNavigationController(initialBackstack = listOf(startDestination))

fun <T> navigationController(initialBackstack: List<T>) =
    NavigationController(initialEntries = initialBackstack.map(::navigationEntry))


fun <T> navigationController(startDestination: T) =
    navigationController(initialBackstack = listOf(startDestination))


fun <T> SavedStateHandle.navigationController(
    key: String? = null,
    initialBackstack: List<T>
) = ReadOnlyProperty<Any, NavigationController<T>> { _, property ->
    val navControllerKey = key ?: property.name
    this.get<NavigationController<T>>(navControllerKey) ?: navigationController(initialBackstack).also {
        this[navControllerKey] = it
    }
}

fun <T> SavedStateHandle.navigationController(
    key: String? = null,
    startDestination: T
) = navigationController(key, kotlin.collections.listOf(startDestination))


@Stable
class NavigationController<T> internal constructor(
    initialEntries: List<NavigationEntry<T>>,
    initialAction: NavigationAction = NavigationAction.Idle
) {

    private val entries = mutableStateOf(initialEntries)

    private val action = mutableStateOf<NavigationAction>(initialAction)


    val backstack = NavigationBackstack(entries, action)

    @Suppress("MemberVisibilityCanBePrivate")
    var onBackstackChange: ((backstack: NavigationBackstack<T>) -> Unit)? = null


    @MainThread
    fun setNewBackstackEntries(entries: List<NavigationEntry<T>>, action: NavigationAction = NavigationAction.Navigate) {
        this.entries.value = entries.toList() // protection from outer modifications
        this.action.value = action
        onBackstackChange?.invoke(backstack)
    }

    override fun toString(): String {
        return "NavController(entries=${entries.value}, action=${action.value})"
    }

    companion object CREATOR : Parcelable.ClassLoaderCreator<NavigationController<*>> {

        override fun createFromParcel(
            parcel: Parcel,
            nullableClassLoader: ClassLoader?
        ): NavigationController<*> {
            val classLoader = nullableClassLoader ?: this::class.java.classLoader
            val entryMap = hashMapOf<NavigationId, NavigationEntry<Any?>>()
            return NavigationController(
                initialEntries = List(parcel.readInt()) {
                    val id = parcel.readParcelable<NavigationId>(classLoader)!!
                    entryMap.getOrPut(id) {

                        NavigationEntry(
                            id = id,
                            destination = parcel.readValue(classLoader)
                        )
                    }
                },
                initialAction = parcel.readParcelable(classLoader)!!
            )
        }

        override fun createFromParcel(parcel: Parcel): NavigationController<*> =
            createFromParcel(parcel, null)

        override fun newArray(size: Int): Array<NavigationController<*>?> {
            return arrayOfNulls(size)
        }

    }

}


@Stable
class NavigationBackstack<T> internal constructor(
    entriesState: State<List<NavigationEntry<T>>>,
    actionState: State<NavigationAction>
) {

    val entries: List<NavigationEntry<T>> by entriesState

    val action: NavigationAction by actionState

    override fun toString(): String {
        return "NavBackstack(entries=$entries, action=$action)"
    }

}