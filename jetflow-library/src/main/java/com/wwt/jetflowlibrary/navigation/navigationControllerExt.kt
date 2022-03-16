package com.wwt.jetflowlibrary.navigation


fun <T> NavigationController<T>.navigate(destinations: List<T>) {
    setNewBackstackEntries(
        entries = backstack.entries + destinations.map(::navigationEntry),
        action = NavigationAction.Navigate
    )
}


fun <T> NavigationController<T>.navigate(destination: T) =
    navigate(listOf(destination))


fun <T> NavigationController<T>.moveToTop(match: Match = Match.Last, predicate: (T) -> Boolean): Boolean {
    val entryIndex = backstack.entries.indexOf(match, predicate)
    return if (entryIndex >= 0) {
        setNewBackstackEntries(
            entries = if (entryIndex == backstack.entries.lastIndex) {
                backstack.entries
            } else {
                backstack.entries.toMutableList().also {
                    val entry = it.removeAt(entryIndex)
                    it.add(entry)
                }
            },
            action = NavigationAction.Navigate
        )
        true
    } else {
        false
    }
}


fun <T> NavigationController<T>.pop(): Boolean = if (backstack.entries.isNotEmpty()) {
    setNewBackstackEntries(
        entries = backstack.entries.dropLast(1),
        action = NavigationAction.Pop
    )
    true
} else {
    false
}


fun <T> NavigationController<T>.popAll() {
    setNewBackstackEntries(
        entries = emptyList(),
        action = NavigationAction.Pop
    )
}


fun <T> NavigationController<T>.popUpTo(
    inclusive: Boolean = false,
    match: Match = Match.Last,
    predicate: (T) -> Boolean
): Boolean {
    val entryIndex = backstack.entries.indexOf(match, predicate)
    return if (entryIndex >= 0) {
        setNewBackstackEntries(
            entries = backstack.entries.subList(
                fromIndex = 0,
                toIndex = if (inclusive) entryIndex else entryIndex + 1
            ),
            action = NavigationAction.Pop
        )
        true
    } else {
        false
    }
}


fun <T> NavigationController<T>.replaceLast(newDestinations: List<T>): Boolean =
    if (backstack.entries.isNotEmpty()) {
        setNewBackstackEntries(
            entries = backstack.entries.dropLast(1) + newDestinations.map(::navigationEntry),
            action = NavigationAction.Replace
        )
        true
    } else {
        false
    }


fun <T> NavigationController<T>.replaceLast(newDestination: T): Boolean =
    replaceLast(listOf(newDestination))


fun <T> NavigationController<T>.replaceAll(newDestinations: List<T>) {
    setNewBackstackEntries(
        entries = newDestinations.map(::navigationEntry),
        action = NavigationAction.Replace
    )
}


fun <T> NavigationController<T>.replaceAll(newDestination: T) =
    replaceAll(listOf(newDestination))


fun <T> NavigationController<T>.replaceUpTo(
    newDestinations: List<T>,
    inclusive: Boolean = false,
    match: Match = Match.Last,
    predicate: (T) -> Boolean
): Boolean {
    val entryIndex = backstack.entries.indexOf(match, predicate)
    return if (entryIndex >= 0) {
        setNewBackstackEntries(
            entries = backstack.entries.subList(
                fromIndex = 0,
                toIndex = if (inclusive) entryIndex else entryIndex + 1
            ) + newDestinations.map(::navigationEntry),
            action = NavigationAction.Replace
        )
        true
    } else {
        false
    }
}


fun <T> NavigationController<T>.replaceUpTo(
    newDestination: T,
    inclusive: Boolean = false,
    match: Match = Match.Last,
    predicate: (T) -> Boolean
): Boolean = replaceUpTo(listOf(newDestination), inclusive, match, predicate)


enum class Match {

    First,
    Last
}

private fun <T> List<NavigationEntry<T>>.indexOf(
    match: Match,
    predicate: (T) -> Boolean
): Int {
    val entryPredicate: (NavigationEntry<T>) -> Boolean = { predicate(it.destination) }
    return when (match) {
        Match.First -> indexOfFirst(entryPredicate)
        Match.Last -> indexOfLast(entryPredicate)
    }
}