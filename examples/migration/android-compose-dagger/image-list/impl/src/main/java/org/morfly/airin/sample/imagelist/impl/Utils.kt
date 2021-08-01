package org.morfly.airin.sample.imagelist.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.transformLatest


/**
 * This is an exact copy of the [flatMapLatest] from coroutines library.
 * Serves as a temporary solution to overcome an internal Kotlin compiler error that happens when
 * calling this function from coroutines library.
 */
@ExperimentalCoroutinesApi
inline fun <T, R> Flow<T>.flatMapLatest(crossinline transform: suspend (value: T) -> Flow<R>): Flow<R> =
    transformLatest { emitAll(transform(it)) }