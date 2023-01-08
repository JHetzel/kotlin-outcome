package de.juliushetzel.outcome

import kotlin.jvm.JvmInline

interface FailureReason

@JvmInline
value class UnknownFailureReason internal constructor(val cause: Throwable) : FailureReason

class ComposedFailureReason private constructor(reasons: Set<FailureReason>) : FailureReason,
    Set<FailureReason> by reasons {
    override fun toString(): String =
        "ComposedFailureReason([${joinToString(", ")}])"

    companion object {
        internal operator fun invoke(reasons: Set<FailureReason>) =
            ComposedFailureReason(reasons.flatMap { reason ->
                if (reason is ComposedFailureReason) reason
                else setOf(reason)
            }.toSet())
    }
}