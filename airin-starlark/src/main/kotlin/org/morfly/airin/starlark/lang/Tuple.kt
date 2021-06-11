package org.morfly.airin.starlark.lang


/**
 *
 */
interface Tuple {

    /**
     *
     */
    val items: List<Any?>
}

/**
 *
 */
fun Tuple.toList(): List<Any?> = items

/**
 *
 */
fun List<Any?>.toTuple(): Tuple = TupleImpl(this)


@JvmInline
internal value class TupleImpl(override val items: List<Any?>) : Tuple

internal object EmptyTuple : Tuple {
    override val items: List<Any?> = emptyList()
}

internal fun tupleOf(vararg items: Any?): Tuple =
    TupleImpl(items.toList())

internal fun emptyTuple(): Tuple =
    EmptyTuple