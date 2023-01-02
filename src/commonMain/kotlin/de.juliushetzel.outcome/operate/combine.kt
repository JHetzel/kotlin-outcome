package de.juliushetzel.outcome.operate

import de.juliushetzel.outcome.ComposedFailureReason
import de.juliushetzel.outcome.FailureReason
import de.juliushetzel.outcome.Outcome

/**
 * applies [transform] on this and [outcome] and returns a
 * resulting [Outcome]. Use [combineStrategy] for specific
 * behaviour in terms of failures.
 *
 * @return an instance of [Outcome]
 */
fun <A : Any, B : Any, C : Any> Outcome<A>.combineWith(
    outcome: Outcome<B>,
    combineStrategy: CombineStrategy = CombineStrategy.ComposeFailures,
    transform: (A, B) -> C
): Outcome<C> =
    combineInternal(
        outcomes = listOf(this, outcome),
        combineStrategy = combineStrategy,
        mapper = { data ->
            @Suppress("UNCHECKED_CAST")
            transform(data[0] as A, data[1] as B)
        }
    )

/**
 * applies [transform] on this, [outcome0] and [outcome1]
 * and returns a resulting [Outcome]. Use [combineStrategy]
 * for specific behaviour in terms of failures.
 *
 * @return an instance of [Outcome]
 */
fun <A : Any, B : Any, C : Any, D : Any> Outcome<A>.combineWith(
    outcome0: Outcome<B>,
    outcome1: Outcome<C>,
    combineStrategy: CombineStrategy = CombineStrategy.ComposeFailures,
    transform: (A, B, C) -> D
): Outcome<D> =
    combineInternal(
        outcomes = listOf(this, outcome0, outcome1),
        combineStrategy = combineStrategy,
        mapper = { data ->
            @Suppress("UNCHECKED_CAST")
            transform(data[0] as A, data[1] as B, data[2] as C)
        }
    )

/**
 * applies [transform] on this, [outcome0], [outcome1] and [outcome2]
 * and returns a resulting [Outcome]. Use [combineStrategy]
 * for specific behaviour in terms of failures.
 *
 * @return an instance of [Outcome]
 */
@Suppress("FunctionName")
fun <A : Any, B : Any, C : Any, D : Any, E : Any> Outcome<A>.combineWith(
    outcome0: Outcome<B>,
    outcome1: Outcome<C>,
    outcome2: Outcome<D>,
    combineStrategy: CombineStrategy = CombineStrategy.ComposeFailures,
    transform: (A, B, C, D) -> E
): Outcome<E> =
    combineInternal(
        outcomes = listOf(this, outcome0, outcome1, outcome2),
        combineStrategy = combineStrategy,
        mapper = { data ->
            @Suppress("UNCHECKED_CAST")
            transform(data[0] as A, data[1] as B, data[2] as C, data[3] as D)
        }
    )

/**
 * applies [transform] on this, [outcome0], [outcome1], [outcome2]
 * and [outcome3] and returns a resulting [Outcome]. Use
 * [combineStrategy] for specific behaviour in terms of failures.
 *
 * @return an instance of [Outcome]
 */
@Suppress("FunctionName")
fun <A : Any, B : Any, C : Any, D : Any, E : Any, F : Any> Outcome<A>.combineWith(
    outcome0: Outcome<B>,
    outcome1: Outcome<C>,
    outcome2: Outcome<D>,
    outcome3: Outcome<E>,
    combineStrategy: CombineStrategy = CombineStrategy.ComposeFailures,
    transform: (A, B, C, D, E) -> F
): Outcome<F> =
    combineInternal(
        outcomes = listOf(this, outcome0, outcome1, outcome2, outcome3),
        combineStrategy = combineStrategy,
        mapper = { data ->
            @Suppress("UNCHECKED_CAST")
            transform(data[0] as A, data[1] as B, data[2] as C, data[3] as D, data[4] as E)
        }
    )

/**
 * a strategy for combining partial failure and success outcomes
 */
fun interface CombineStrategy {

    fun handle(data: List<Any>, failures: List<FailureReason>, mapper: (List<Any>) -> Any): Outcome<Any>

    companion object {

        /**
         * compose failure reasons into [ComposedFailureReason]
         */
        val ComposeFailures: CombineStrategy = ComposeFailuresStrategy

        /**
         * provide a specific [reason] when merging outcomes
         */
        fun SpecificFailure(reason: FailureReason): CombineStrategy = SpecificFailureStrategy(reason)
    }
}

private fun <T : Any> combineInternal(
    outcomes: List<Outcome<Any>>,
    mapper: (List<Any>) -> T,
    combineStrategy: CombineStrategy
): Outcome<T> {
    check(outcomes.isNotEmpty()) { "Can not combine 0 Outcomes" }

    val data = outcomes.filterIsInstance<Outcome.Success<*>>().mapNotNull { it.value }
    val failureReasons = outcomes.filterIsInstance<Outcome.Failure>().map { it.reason }
    @Suppress("UNCHECKED_CAST")
    return combineStrategy.handle(data, failureReasons, mapper) as Outcome<T>
}

@Suppress("PrivatePropertyName")
private val ComposeFailuresStrategy = CombineStrategy { data, reasons, mapper ->
    when {
        reasons.isEmpty() -> Outcome.Success(mapper(data))
        reasons.size == 1 -> Outcome.Failure(reasons[0])
        else -> Outcome.Failure(ComposedFailureReason(reasons.toSet()))
    }
}

@Suppress("FunctionName")
private fun SpecificFailureStrategy(reason: FailureReason) = CombineStrategy { data, reasons, mapper ->
    when {
        reasons.isEmpty() -> Outcome.Success(mapper(data))
        else -> Outcome.Failure(reason)
    }
}