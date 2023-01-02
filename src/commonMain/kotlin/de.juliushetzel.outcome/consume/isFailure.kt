package de.juliushetzel.outcome.consume

import de.juliushetzel.outcome.Outcome
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * @return true if this is [Outcome.Failure]]
 */
@OptIn(ExperimentalContracts::class)
fun <A> Outcome<A>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Outcome.Failure)
        returns(false) implies (this@isFailure is Outcome.Success<A>)
    }
    return this is Outcome.Failure
}