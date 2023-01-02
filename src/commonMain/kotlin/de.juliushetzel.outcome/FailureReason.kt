package de.juliushetzel.outcome

import kotlin.jvm.JvmInline

interface FailureReason

@JvmInline
value class UnknownFailureReason internal constructor(val cause: Throwable) : FailureReason

class ComposedFailureReason internal constructor(reasons: Set<FailureReason>) : FailureReason {
    val reasons: Set<FailureReason> = reasons.flatMap { reason ->
        if(reason is ComposedFailureReason) reason.reasons
        else setOf(reason)
    }.toSet()

    fun contains(reason: FailureReason) = reasons.contains(reason)

    override fun toString(): String =
        "ComposedFailureReason([${reasons.joinToString(", ")}])"
}