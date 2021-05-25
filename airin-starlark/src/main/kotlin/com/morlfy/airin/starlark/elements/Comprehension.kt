package com.morlfy.airin.starlark.elements

import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
sealed class Comprehension(
    internal val body: Expression?,
    internal val clauses: MutableList<Clause>
) : Expression {

    /**
     *
     */
    sealed interface Clause : Element

    /**
     *
     */
    class For(
        val variables: List<Reference?>,
        val iterable: Expression?
    ) : Clause {

        override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
            visitor.visit(this, position, mode, accumulator)
        }
    }

    /**
     *
     */
    class If(
        val condition: Expression?
    ) : Clause {

        override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
            visitor.visit(this, position, mode, accumulator)
        }
    }
}

/**
 *
 */
open class ListComprehension<out T> internal constructor(
    body: Expression?,
    clauses: MutableList<Clause>
) : Comprehension(body, clauses),
    List<T> by emptyList() {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
class DictionaryComprehension<K, V : Value>(
    body: Expression?,
    clauses: MutableList<Clause>
) : Comprehension(body, clauses),
    Map<K, V> by emptyMap() {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}
