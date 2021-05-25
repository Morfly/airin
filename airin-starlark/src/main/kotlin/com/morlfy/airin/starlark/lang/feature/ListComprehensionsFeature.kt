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