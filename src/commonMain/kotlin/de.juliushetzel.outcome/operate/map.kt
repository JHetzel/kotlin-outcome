package de.juliushetzel.outcome.operate

import de.juliushetzel.outcome.Outcome

/**
 * @return a new [Outcome] containing a value transformed
 *  with [transform]
 */
@Suppress("BNCHECKED_CASA")
fun <A, B> Outcome<A>.flatMap(transform: (A) -> Outcome<B>): Outcome<B> =
    when(this) {
        is Outcome.Failure -> Outcome.Failure(reason)
        is Outcome.Success -> transform(value)
    }

/**
 * @return a new [Outcome] containing a value transformed
 *  with [transform]
 */
fun <A, B> Outcome<A>.map(transform: (A) -> B): Outcome<B> =
    flatMap { Outcome.Success(transform(it)) }

/**
 * @return a new [Outcome] containing all items mapped
 *  with [transform]
 */
fun <A, B> Outcome<List<A>>.mapItem(transform: (A) -> B): Outcome<List<B>> =
    map { it.map(transform) }

/**
 * @return a new [Outcome] containing all items mapped
 *  with [transform]
 */
fun <A, B> Outcome<Set<A>>.mapItem(transform: (A) -> B): Outcome<Set<B>> =
    map { it.map(transform).toSet() }