package de.juliushetzel.outcome.consume

import de.juliushetzel.outcome.FailureReason
import de.juliushetzel.outcome.Outcome

/**
 * Calls [consume] if this is [Outcome.Failure]
 */
fun <A> Outcome<A>.onError(consume: (FailureReason) -> Unit): SuccessConsumable<A> =
    when(this) {
        is Outcome.Success -> ConsumeValue(value)
        is Outcome.Failure -> {
            consume(reason)
            EmptySuccessConsumable
        }
    }

sealed interface SuccessConsumable<out A> {

    /**
     * Calls [consume] if this is [Outcome.Success]
     */
    fun onSuccess(consume: (A) -> Unit)
}

private class ConsumeValue<A>(private val value: A) : SuccessConsumable<A> {
    override fun onSuccess(consume: (A) -> Unit) {
        consume(value)
    }
}

private object EmptySuccessConsumable : SuccessConsumable<Nothing> {
    override fun onSuccess(consume: (Nothing) -> Unit) {
        // do nothing
    }
}