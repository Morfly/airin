package com.morlfy.airin.starlark.elements

import com.morlfy.airin.starlark.lang.Value


///**
// *
// */
//@JvmInline
//value class RawText(val value: String) : Statement, Expression

/**
 *
 */
sealed interface RawText : Element {

    /**
     *
     */
    val value: String

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

@JvmInline
value class RawStatement(override val value: String) : RawText, Statement

/**
 *
 */
class StringRawExpression(override val value: String) : RawText, Expression,
    CharSequence by value

/**
 *
 */
class ListRawExpression<T>(override val value: String) : RawText, Expression,
    List<T> by emptyList()

/**
 *
 */
class DictionaryRawExpression<K, V : Value>(override val value: String) : RawText, Expression,
    Map<K, V> by emptyMap()

/**
 *
 */
@JvmInline
value class AnyRawExpression(override val value: String) : RawText, Expression