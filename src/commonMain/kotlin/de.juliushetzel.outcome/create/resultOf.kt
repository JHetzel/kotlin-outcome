package de.juliushetzel.outcome.create

import de.juliushetzel.outcome.FailureReason
import de.juliushetzel.outcome.Outcome
import de.juliushetzel.outcome.UnknownFailureReason

/**
 * @return an instance of [Outcome.Failure]
 */
fun <A> outcomeOf(failureReason: FailureReason): Outcome<A> =
    Outcome.Failure(failureReason)

/**
 * @return an instance of [Outcome.Success]
 */
fun <A> outcomeOf(value: A): Outcome<A> =
    Outcome.Success(value)

/**
 * @return an instance of [Outcome.Success]
 */
fun <A> outcomeOf(run: () -> A): Outcome<A> =
    Outcome.Success(run())

/**
 * @return an instance of [Outcome.Failure] if an
 *  exception was thrown, [Outcome.Success] otherwise
 */
fun <A> safeOutcomeOf(run: () -> A): Outcome<A> =
    try {
        Outcome.Success(run())
    } catch (throwable: Throwable) {
        Outcome.Failure(UnknownFailureReason(throwable))
    }