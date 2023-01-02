package de.juliushetzel.outcome.operate

import de.juliushetzel.outcome.Outcome

/**
 * @return a new [Outcome] containing all items
 *  matching the [predicate]
 */
fun <A> Outcome<List<A>>.filter(predicate: (A) -> Boolean): Outcome<List<A>> =
    map { it.filter(predicate) }

/**
 * @return a new [Outcome] containing all items
 *  not matching the [predicate]
 */
fun <A> Outcome<List<A>>.filterNot(predicate: (A) -> Boolean): Outcome<List<A>> =
    map { it.filterNot(predicate) }