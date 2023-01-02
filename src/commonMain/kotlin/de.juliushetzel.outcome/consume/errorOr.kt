package de.juliushetzel.outcome.consume

import de.juliushetzel.outcome.FailureReason
import de.juliushetzel.outcome.Outcome

/**
 * @return a failureReason if this is [Outcome.Failure]
 *  [default] otherwise
 */
fun <A> Outcome<A>.failureOr(default: () -> FailureReason): FailureReason =
    when(this) {
        is Outcome.Failure -> reason
        is Outcome.Success -> default()
    }

/**
 * @return a failureReason if this is [Outcome.Failure]
 *  [default] otherwise
 */
fun <A> Outcome<A>.failureOr(default: FailureReason): FailureReason =
    when(this) {
        is Outcome.Failure -> reason
        is Outcome.Success -> default
    }

/**
 * @return a failureReason if this is [Outcome.Failure]
 *  null otherwise
 */
fun <A> Outcome<A>.failureOrNull(): FailureReason? =
    when(this) {
        is Outcome.Failure -> reason
        is Outcome.Success -> null
    }