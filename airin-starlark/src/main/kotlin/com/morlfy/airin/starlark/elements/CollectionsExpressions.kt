@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.elements


/**
 *
 */
@JvmInline
value class ListExpression(val value: List<Expression?>) : Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
fun <T> ListExpression(list: List<T>): ListExpression =
    ListExpression(list.map(::Expression))

/**
 *
 */
@JvmInline
value class DictionaryExpression(val value: Map<Expression?, Expression?>) : Expression {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
fun DictionaryExpression(dictionary: Map<*, *>): DictionaryExpression =
    DictionaryExpression(value = dictionary
        .mapKeys { (key, _) -> Expression(key) }
        .mapValues { (_, v) -> Expression(v) }
    )