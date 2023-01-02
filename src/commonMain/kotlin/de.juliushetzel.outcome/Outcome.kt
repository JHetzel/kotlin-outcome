package de.juliushetzel.outcome

sealed class Outcome<out A> {

    data class Success<A>(val value: A) : Outcome<A>()

    data class Failure(val reason: FailureReason) : Outcome<Nothing>()
}