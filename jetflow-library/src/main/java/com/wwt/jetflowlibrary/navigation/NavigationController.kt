package com.wwt.jetflowlibrary.navigation

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
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
) = navigationController(key, listOf(startDestination))

@Stable
class NavigationController<T> internal constructor(
    initialEntries: List<NavigationEntry<T>>,
    initialAction: NavigationAction = NavigationAction.Idle
) : Parcelable {

    private val entries = mutableStateOf(initialEntries)

    private val action = mutableStateOf(initialAction)


    val backstack = NavigationBackstack(entries, action)

    @Suppress("MemberVisibilityCanBePrivate")
    var onBackstackChange: ((backstack: NavigationBackstack<T>) -> Unit)? = null


    @MainThread
    fun setNewBackstackEntries(entries: List<NavigationEntry<T>>, action: NavigationAction = NavigationAction.Navigate) {
        this.entries.value = entries.toList() // protection from outer modifications
        this.action.value = action
        onBackstackChange?.invoke(backstack)
    }

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        entries.value.let { backstack ->
            // This handles the case when some NavEntries instance appear in the backstack several
            // times. After the restoration their instances must not be duplicated.
            val ids = hashSetOf<NavigationId>()
            parcel.writeInt(backstack.size)
            backstack.forEach {
                // write every id to preserve the order
                parcel.writeParcelable(it.id, flags)
                if (it.id !in ids) {
                    ids.add(it.id)
                    // but write a destination only once, because there is no need to duplicate
                    // existing data
                    parcel.writeValue(it.destination)
                }
            }
        }
        parcel.writeParcelable(action.value, flags)
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