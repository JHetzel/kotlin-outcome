package de.juliushetzel.outcome.operate

import de.juliushetzel.outcome.FailureReason
import de.juliushetzel.outcome.Outcome
import de.juliushetzel.outcome.consume.isFailure
import de.juliushetzel.outcome.consume.isSuccess

/**
 * calls [consume] as a side effect if this is [Outcome.Success]
 */
fun <A> Outcome<A>.alsoOnSuccess(consume: (A) -> Unit): Outcome<A> {
    if (isSuccess()) consume(value)
    return this
}

/**
 * calls [consume] as a side effect if this is [Outcome.Failure]
 */
fun <A> Outcome<A>.alsoOnError(consume: (FailureReason) -> Unit): Outcome<A> {
    if (isFailure()) consume(reason)
    return this
}