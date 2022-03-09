package com.wwt.jetflowlibrary.navigation

import android.app.Application
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import kotlinx.parcelize.Parcelize

@Composable
internal fun <T> rememberNavigationComponentHolder(
    backstack: NavigationBackstack<T>
): NavigationComponentHolder<T> {
    val saveableStateHolder = rememberSaveableStateHolder()
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val savedStateRegistry = LocalSavedStateRegistryOwner.current.savedStateRegistry

    // applicationContext may be not Application in IDE preview. Handle it gracefully here.
    val application = LocalContext.current.applicationContext as? Application

    return rememberSaveable(
        saver = listSaver(
            save = { listOf(it.id, it.componentIds.toTypedArray()) },
            restore = { restored ->
                @Suppress("UNCHECKED_CAST")
                (NavigationComponentHolder(
                    id = restored[0] as NavigationHolderId,
                    restoredComponentIds = (restored[1] as Array<Parcelable>).map { it as NavigationId }
                        .toSet(),
                    initialBackstack = backstack,
                    saveableStateHolder = saveableStateHolder,
                    navHostViewModelStoreOwner = viewModelStoreOwner,
                    navHostLifecycle = lifecycle,
                    navHostSavedStateRegistry = savedStateRegistry,
                    application = application
                ))
            }
        )
    ) {
        NavigationComponentHolder(
            initialBackstack = backstack,
            saveableStateHolder = saveableStateHolder,
            navHostViewModelStoreOwner = viewModelStoreOwner,
            navHostLifecycle = lifecycle,
            navHostSavedStateRegistry = savedStateRegistry,
            application = application
        )
    }.apply {
        // support setting new backstacks
        this.backstack = backstack
    }
}

private const val PACKAGE_KEY = "dev.olshevski.navigation.reimagined.key"

/**
 * Used for differentiating holder id from entry ids, because when everything is named id it is
 * just confusing.
 */
@Parcelize
@JvmInline
internal value class NavigationHolderId(private val id: NavigationId = NavigationId()) : Parcelable {
    override fun toString(): String = id.toString()
}

/**
 * Stores and manages all components (lifecycles, saved states, view models).
 */
internal class NavigationComponentHolder<T>(
    val id: NavigationHolderId = NavigationHolderId(),
    restoredComponentIds: Set<NavigationId> = emptySet(),
    initialBackstack: NavigationBackstack<T>,
    private val saveableStateHolder: SaveableStateHolder,
    navHostViewModelStoreOwner: ViewModelStoreOwner,
    private val navHostLifecycle: Lifecycle,
    private val navHostSavedStateRegistry: SavedStateRegistry,
    private val application: Application?
) {

    var backstack by mutableStateOf(initialBackstack)

    private val backstackIds by derivedStateOf {
        backstack.entries.map { it.id }.toHashSet()
    }

    private val viewModelStoreProvider: ViewModelStoreProvider =
        ViewModelProvider(navHostViewModelStoreOwner)["$PACKAGE_KEY:$id", NavigationHostViewModel::class.java]

    private var navHostLifecycleState: Lifecycle.State = Lifecycle.State.INITIALIZED

    private val componentEntries = mutableMapOf<NavigationId, NavigationComponentEntry<T>>()

    val componentIds get() = componentEntries.keys as Set<NavigationId>

    init {
        // We need to restore all previous component entries, which in return restore their own
        // saved states and reconnect all SavedStateHandles to a new SavedStateRegistry.

        // Do not restore components that are no longer present in the backstack.
        // Remove their associated components instead.
        restoredComponentIds.filter { it !in backstackIds }.forEach { removeComponents(it) }

        // Everything else is recreated.
        backstack.entries.filter { it.id in restoredComponentIds }.forEach { entry ->
            componentEntries.getOrPut(entry.id) {
                newComponentEntry(entry)
            }
        }
    }

    private val lifecycleEventObserver = LifecycleEventObserver { _, event ->
        navHostLifecycleState = event.targetState
        componentEntries.values.forEach {
            it.navHostLifecycleState = navHostLifecycleState
        }
    }

    val lastComponentEntry by derivedStateOf {
        backstack.entries.lastOrNull()?.let { lastEntry ->
            componentEntries.getOrPut(lastEntry.id) {
                newComponentEntry(lastEntry)
            }
        }
    }.also { it ->
        // this block will be executed only when a new distinct entry is set
        val newLastComponentEntry = it.value

        componentEntries.values
            .filter { it != newLastComponentEntry }
            .forEach {
                it.maxLifecycleState = minOf(it.maxLifecycleState, Lifecycle.State.STARTED)
            }
        newLastComponentEntry?.maxLifecycleState = Lifecycle.State.STARTED
    }

    private fun newComponentEntry(entry: NavigationEntry<T>): NavigationComponentEntry<T> {
        val componentEntry = NavigationComponentEntry(
            entry = entry,
            saveableStateHolder = saveableStateHolder,
            viewModelStore = viewModelStoreProvider.getViewModelStore(entry.id),
            application = application
        )

        // state should be restored only in INITIALIZED state
        savedStateKey(componentEntry.id).let { key ->
            navHostSavedStateRegistry.consumeRestoredStateForKey(key).let { savedState ->
                componentEntry.restoreState(savedState ?: Bundle())
            }
            navHostSavedStateRegistry.unregisterSavedStateProvider(key)
            navHostSavedStateRegistry.registerSavedStateProvider(
                key,
                componentEntry.savedStateProvider
            )
        }

        // apply actual states only after state restoration
        componentEntry.navHostLifecycleState = navHostLifecycleState
        componentEntry.maxLifecycleState = Lifecycle.State.STARTED

        return componentEntry
    }

    private fun savedStateKey(entryId: NavigationId) = "$PACKAGE_KEY:$id:$entryId"

    fun onCreate() {
        cleanupComponentEntries()

        // When created there is no transition, and we need to apply proper lifecycle states
        // immediately.
        setPostTransitionLifecycleStates()

        navHostLifecycle.addObserver(lifecycleEventObserver)
    }

    fun onTransitionFinish() {
        cleanupComponentEntries()
        setPostTransitionLifecycleStates()
        // https://www.youtube.com/watch?v=cwyTleTL06Y
    }

    /**
     * Remove entries that are no longer in the backstack.
     */
    private fun cleanupComponentEntries() {
        componentEntries.keys.filter { it !in backstackIds }.forEach { entryId ->
            componentEntries.remove(entryId)?.let { componentEntry ->
                componentEntry.maxLifecycleState = Lifecycle.State.DESTROYED
            }
            removeComponents(entryId)
        }
    }

    /**
     * Unregister saved state provider and cleanup view models for the specified entry id.
     */
    private fun removeComponents(entryId: NavigationId) {
        navHostSavedStateRegistry.unregisterSavedStateProvider(savedStateKey(entryId))
        viewModelStoreProvider.removeViewModelStore(entryId)
        saveableStateHolder.removeState(entryId)
    }

    private fun setPostTransitionLifecycleStates() {
        // last entry is resumed, everything else is stopped
        componentEntries.values
            .filter { it != lastComponentEntry }
            .forEach {
                it.maxLifecycleState = Lifecycle.State.CREATED
            }
        lastComponentEntry?.maxLifecycleState = Lifecycle.State.RESUMED
    }

    fun onDispose() {
        navHostLifecycle.removeObserver(lifecycleEventObserver)
        componentEntries.values.forEach {
            it.navHostLifecycleState = Lifecycle.State.DESTROYED
        }
    }

}

private interface ViewModelStoreProvider {

    fun getViewModelStore(id: NavigationId): ViewModelStore
    fun removeViewModelStore(id: NavigationId)

}

internal class NavigationHostViewModel : ViewModel(), ViewModelStoreProvider {

    private val viewModelStores = mutableMapOf<NavigationId, ViewModelStore>()

    override fun getViewModelStore(id: NavigationId) = viewModelStores.getOrPut(id) {
        ViewModelStore()
    }

    override fun removeViewModelStore(id: NavigationId) {
        viewModelStores.remove(id)?.also { it.clear() }
    }

    override fun onCleared() {
        for (store in viewModelStores.values) {
            store.clear()
        }
        viewModelStores.clear()
    }

}