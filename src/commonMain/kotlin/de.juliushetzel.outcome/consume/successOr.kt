package de.juliushetzel.outcome.consume

import de.juliushetzel.outcome.Outcome

/**
 * @return the value if this is [Outcome.Success]
 *  [default] otherwise
 */
fun <A> Outcome<A>.successOr(default: () -> A): A =
    when(this) {
        is Outcome.Failure -> default()
        is Outcome.Success -> value
    }

/**
 * @return the value if this is [Outcome.Success]
 *  [default] otherwise
 */
fun <A> Outcome<A>.successOr(default: A): A =
    when(this) {
        is Outcome.Failure -> default
        is Outcome.Success -> value
    }

/**
 * @return the value if this is [Outcome.Success]
 *  null otherwise
 */
fun <A> Outcome<A>.successOrNull(): A? =
    when(this) {
        is Outcome.Failure -> null
        is Outcome.Success -> value
    }

/**
 * @return true if this is [Outcome.Success]
 *  false otherwise
 */
fun Outcome<Boolean>.successOrFalse(): Boolean =
    when(this) {
        is Outcome.Failure -> false
        is Outcome.Success -> value
    }

/**
 * @return false if this is [Outcome.Success]
 *  true otherwise
 */
fun Outcome<Boolean>.successOrTrue(): Boolean =
    when(this) {
        is Outcome.Failure -> true
        is Outcome.Success -> value
    }