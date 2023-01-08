package de.juliushetzel.outcome.consume

import de.juliushetzel.outcome.FailureReason
import de.juliushetzel.outcome.Outcome

/**
 * Calls [consume] if this is [Outcome.Success]
 */
fun <A> Outcome<A>.onSuccess(consume: (A) -> Unit): FailureConsumable =
    when(this) {
        is Outcome.Failure -> ConsumeFailure(reason)
        is Outcome.Success -> {
            consume(value)
            EmptyFailureConsumable
        }
    }

sealed class FailureConsumable {

    /**
     * Calls [consume] if this is [Outcome.Failure]
     */
    abstract fun onFailure(consume: (FailureReason) -> Unit)
}

private class ConsumeFailure(private val reason: FailureReason) : FailureConsumable() {
    override fun onFailure(consume: (FailureReason) -> Unit) {
        consume(reason)
    }
}

private object EmptyFailureConsumable : FailureConsumable() {
    override fun onFailure(consume: (FailureReason) -> Unit) {
        // do nothing
    }
}