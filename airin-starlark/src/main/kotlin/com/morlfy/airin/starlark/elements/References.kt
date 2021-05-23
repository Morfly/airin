package com.morlfy.airin.starlark.elements

import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
sealed interface Reference : Expression {

    /**
     *
     */
    val name: String

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}

/**
 *
 */
class StringReference(override val name: String) : Reference,
    StringType by name

/**
 *
 */
class ListReference<out T>(override val name: String) : Reference,
    List<T> by emptyList()

/**
 * TODO
 */
class DictionaryReference<K /*: Key*/, V : Value>(override val name: String) : Reference,
    Map<K, V> by emptyMap()

/**
 *
 */
@JvmInline
value class AnyReference(override val name: String) : Reference