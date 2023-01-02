package de.juliushetzel.outcome.operate

import de.juliushetzel.outcome.Outcome

/**
 * @return a new outcome with a negated value
 */
fun Outcome<Boolean>.not(): Outcome<Boolean> = map { it.not() }