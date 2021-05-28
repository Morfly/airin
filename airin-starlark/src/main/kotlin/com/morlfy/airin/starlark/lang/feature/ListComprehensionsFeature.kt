/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("ClassName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.BooleanType
import com.morlfy.airin.starlark.lang.FloatType
import com.morlfy.airin.starlark.lang.IntegerType
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.api.LanguageContext
import com.morlfy.airin.starlark.lang.api.LanguageContextProvider
import com.morlfy.airin.starlark.lang.api.LanguageFeature


/**
 *
 */
internal interface ListComprehensionsFeature<C : LanguageContext> : LanguageFeature, LanguageContextProvider<C>,
    StarlarkStatementsHolder {

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
            Comprehension.For(variables = listOf(itemVariable), iterable = Expression(iterable, ::ListExpression))
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
    infix fun <R> _CompWithStringItems.take(body: C.(StringReference) -> R): ListComprehension<R> =
        buildComprehension(context = newContext(), variable, body, clauses)

    // ===== Integer items =====

    /**
     *
     */
    class _CompWithIntegerItems(
        internal val variable: IntegerReference,
        internal val clauses: MutableList<Comprehension.Clause>
    )

    /**
     *
     */
    infix fun String.`in`(iterable: List<IntegerType>): _CompWithIntegerItems {
        val itemVariable = IntegerReference(name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), iterable = Expression(iterable, ::ListExpression))
        )
        return _CompWithIntegerItems(itemVariable, clauses)
    }

    /**
     *
     */
    infix fun _CompWithIntegerItems.`if`(condition: String): _CompWithIntegerItems {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }

    /**
     *
     */
    infix fun <R> _CompWithIntegerItems.take(body: C.(IntegerReference) -> R): ListComprehension<R> =
        buildComprehension(context = newContext(), variable, body, clauses)

    // ===== Float items =====

    /**
     *
     */
    class _CompWithFloatItems(
        internal val variable: FloatReference,
        internal val clauses: MutableList<Comprehension.Clause>
    )

    /**
     *
     */
    infix fun String.`in`(iterable: List<FloatType>): _CompWithFloatItems {
        val itemVariable = FloatReference(name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), iterable = Expression(iterable, ::ListExpression))
        )
        return _CompWithFloatItems(itemVariable, clauses)
    }

    /**
     *
     */
    infix fun _CompWithFloatItems.`if`(condition: String): _CompWithFloatItems {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }

    /**
     *
     */
    infix fun <R> _CompWithFloatItems.take(body: C.(FloatReference) -> R): ListComprehension<R> =
        buildComprehension(context = newContext(), variable, body, clauses)

    // ===== Boolean items =====

    /**
     *
     */
    class _CompWithBooleanItems(
        internal val variable: BooleanReference,
        internal val clauses: MutableList<Comprehension.Clause>
    )

    /**
     *
     */
    infix fun String.`in`(iterable: List<BooleanType>): _CompWithBooleanItems {
        val itemVariable = BooleanReference(name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), iterable = Expression(iterable, ::ListExpression))
        )
        return _CompWithBooleanItems(itemVariable, clauses)
    }

    /**
     *
     */
    infix fun _CompWithBooleanItems.`if`(condition: String): _CompWithBooleanItems {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }

    /**
     *
     */
    infix fun <R> _CompWithBooleanItems.take(body: C.(BooleanReference) -> R): ListComprehension<R> =
        buildComprehension(context = newContext(), variable, body, clauses)

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
            Comprehension.For(variables = listOf(itemVariable), Expression(iterable, ::ListExpression))
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
    infix fun <T, R> _CompWithListItems<T>.`for`(body: C.(ListReference<T>) -> ListComprehension<R>): ListComprehension<R> {
        val context = newContext()
        val innerComprehension = context.body(variable)
        if (context.statements.isNotEmpty())
            error("Statements are prohibited in `for` clauses of comprehensions.")
        clauses += innerComprehension.clauses
        return ListComprehension(body = innerComprehension.body, clauses)
    }

    /**
     *
     */
    infix fun <T, R> _CompWithListItems<T>.take(body: C.(ListReference<T>) -> List<R>): ListComprehension<List<R>> =
        buildComprehension(context = newContext(), variable, body, clauses)
}

/**
 *
 */
private fun <C : LanguageContext, V : Reference, R> StarlarkStatementsHolder.buildComprehension(
    context: C,
    variable: V,
    body: C.(V) -> R,
    clauses: MutableList<Comprehension.Clause>
): ListComprehension<R> {
    val compBody = context.body(variable)

    if (context.statements.isNotEmpty()) {
        val exprStatements = context.statements.filterIsInstance<ExpressionStatement>()
        if (exprStatements.size > 1) error("A comprehension body must not contain more than one statement.")
        val expression = exprStatements.first().expression
        val comprehension = ListComprehension<R>(body = expression, clauses)
        statements += ExpressionStatement(comprehension)
        return comprehension
    }
    return ListComprehension(body = Expression(compBody), clauses)
}