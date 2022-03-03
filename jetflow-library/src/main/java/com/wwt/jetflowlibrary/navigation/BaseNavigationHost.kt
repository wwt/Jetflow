package com.wwt.jetflowlibrary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
internal fun <T> BaseNavigationHost(
    backstack: NavigationBackstack<T> //,, param: (kotlin.Any) -> kotlin.Unit){}
//    entryTransition: @Composable (NavComponentEntry<T>?) -> NavComponentEntry<T>?
) {
    // In future, it may be convenient to make possible to create ComponentHolder externally
    // so it is hoistable. But I need to see reasonable use-cases for this.
//    val componentHolder = rememberNavComponentHolder(backstack)

//    val currentComponentEntry = key(componentHolder.id) {
//        entryTransition(componentHolder.lastComponentEntry)

        // For NavHost: currentComponentEntry is the same as lastComponentEntry.
        //
        // For AnimatedNavHost: currentComponentEntry is the entry in transition. When transition
        // finishes, currentComponentEntry will become the same as currentComponentEntry.
//    }

    DisposableEffect("componentHolder", "currentComponentEntry") {
        onDispose {
            // should be called only in onDispose, because it affects lifecycle events
//            componentHolder.onTransitionFinish()
        }
    }
//
//    DisposableEffect(componentHolder) {
//        componentHolder.onCreate()
//        onDispose {
//            componentHolder.onDispose()
//        }
//    }
}

@Composable
fun <T> GetNavigationHost(
    controller: NavigationController<T>,
//    transitionSpec: AnimatedNavHostTransitionSpec<T> = CrossfadeTransitionSpec,
//    emptyBackstackPlaceholder: @Composable AnimatedVisibilityScope.() -> Unit = {},
//    contentSelector: @Composable AnimatedVisibilityScope.(T) -> Unit
) = GetNavHost(
    backstack = controller.backstack,
//    transitionSpec = transitionSpec,
//    emptyBackstackPlaceholder = emptyBackstackPlaceholder,
//    contentSelector = contentSelector
)

@Composable
fun <T> GetNavHost(
    backstack: NavigationBackstack<T>
    //,
//    transitionSpec: AnimatedNavHostTransitionSpec<T> = CrossfadeTransitionSpec,
//    emptyBackstackPlaceholder: @Composable AnimatedVisibilityScope.() -> Unit = {},
//    contentSelector: @Composable AnimatedVisibilityScope.(T) -> Unit
) = BaseNavigationHost(backstack) { lastComponentEntry ->
//    val transition = updateTransition(
//        targetState = lastComponentEntry,
//        label = "AnimatedNavHost"
//    )
//    transition.AnimatedContent(
//        transitionSpec = {
//            selectTransition(transitionSpec, backstack.action)
//        },
//        contentKey = { it?.id }
//    ) { entry ->
//        if (entry != null) {
//            entry.ComponentProvider {
//                contentSelector(entry.destination)
//            }
//        } else {
//            emptyBackstackPlaceholder()
//        }
//    }
//    transition.currentState
}