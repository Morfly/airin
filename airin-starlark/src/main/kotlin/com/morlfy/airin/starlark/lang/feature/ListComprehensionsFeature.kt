@file:Suppress("ClassName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.StringType


/**
 *
 */
internal interface ListComprehensionsFeature : LanguageFeature {

    // ==== String items =====

    /**
     *
     */
    class _CompWithStringItems(
        internal val variable: StringReference,
        internal val clauses: MutableList<Comprehension.Clause>
    )

    /**
     *
     */
    infix fun String.`in`(iterable: List<StringType>): _CompWithStringItems {
        val itemVariable = StringReference(name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), iterable = Expression(iterable))
        )
        return _CompWithStringItems(itemVariable, clauses)
    }

    /**
     *
     */
    infix fun _CompWithStringItems.`if`(condition: String): _CompWithStringItems {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }

    /**
     *
     */
    infix fun <R> _CompWithStringItems.take(body: (StringReference) -> R): ListComprehension<R> {
        val expression = body(variable)
        return ListComprehension(body = Expression(expression), clauses)
    }

    // ===== List items =====

    /**
     *
     */
    class _CompWithListItems<T>(
        internal val variable: ListReference<T>,
        internal val clauses: MutableList<Comprehension.Clause>
    )

    /**
     *
     */
    infix fun <T> String.`in`(iterable: List<List<T>>): _CompWithListItems<T> {
        val itemVariable = ListReference<T>(name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), Expression(iterable))
        )
        return _CompWithListItems(itemVariable, clauses)
    }


    /**
     *
     */
    infix fun <T> _CompWithListItems<T>.`if`(condition: String): _CompWithListItems<T> {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }

    /**
     *
     */
    infix fun <T, R> _CompWithListItems<T>.`for`(body: (ListReference<T>) -> ListComprehension<R>): ListComprehension<R> {
        val innerComprehension = body(variable)
        clauses += innerComprehension.clauses
        return ListComprehension(body = innerComprehension.body, clauses)
    }

    /**
     *
     */
    infix fun <T, R> _CompWithListItems<T>.take(body: (ListReference<T>) -> List<R>): ListComprehension<List<R>> {
        val expression = body(variable)
        return ListComprehension(body = Expression(expression), clauses)
    }
}