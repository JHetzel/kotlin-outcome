package de.juliushetzel.outcome.create

import de.juliushetzel.outcome.FailureReason
import de.juliushetzel.outcome.Outcome
import de.juliushetzel.outcome.UnknownFailureReason

/**
 * @return an instance of [Outcome.Failure]
 */
fun <T> outcomeOf(failureReason: FailureReason): Outcome<T> =
    Outcome.Failure(failureReason)

/**
 * @return an instance of [Outcome.Success]
 */
fun <T> outcomeOf(value: T): Outcome<T> =
    Outcome.Success(value)

/**
 * @return an instance of [Outcome.Success]
 */
fun <T> outcomeOf(run: () -> T): Outcome<T> =
    Outcome.Success(run())

/**
 * @return an instance of [Outcome.Failure] if an
 *  exception was thrown, [Outcome.Success] otherwise
 */
fun <T> safeOutcomeOf(run: () -> T): Outcome<T> =
    try {
        Outcome.Success(run())
    } catch (throwable: Throwable) {
        Outcome.Failure(UnknownFailureReason(throwable))
    }