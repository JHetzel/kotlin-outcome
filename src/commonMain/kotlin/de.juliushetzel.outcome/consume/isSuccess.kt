package de.juliushetzel.outcome.consume

import de.juliushetzel.outcome.Outcome
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * @return true if this is [Outcome.Success]]
 */
@OptIn(ExperimentalContracts::class)
fun <A> Outcome<A>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is Outcome.Success<A>)
        returns(false) implies (this@isSuccess is Outcome.Failure)
    }
    return this is Outcome.Success
}

