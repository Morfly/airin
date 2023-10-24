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

@file:Suppress("ClassName", "RemoveRedundantSpreadOperator", "FunctionName")

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature
import org.morfly.airin.starlark.lang.api.StatementsHolder
import kotlin.reflect.KClass
import kotlin.reflect.typeOf


/**
 * Enables load statements for Starlark file.
 */
internal interface LoadStatementsFeature : LanguageFeature, StatementsHolder {

    /**
     * Starlark Load statement.
     */
    fun load(file: String, vararg symbols: String) {
        val elements = symbols.map {
            LoadStatement.Symbol(
                name = StringLiteral(it),
                alias = null
            )
        }
        statements += LoadStatement(file = StringLiteral(file), symbols = elements)
    }

    fun load(file: String, symbol1: String): _LoadStatementBuilder1 {
        load(file, *arrayOf(symbol1))
        return _LoadStatementBuilder1(symbol1)
    }

    fun load(file: String, symbol1: String, symbol2: String): _LoadStatementBuilder2 {
        load(file, *arrayOf(symbol1, symbol2))
        return _LoadStatementBuilder2(symbol1, symbol2)
    }

    fun load(file: String, symbol1: String, symbol2: String, symbol3: String): _LoadStatementBuilder3 {
        load(file, *arrayOf(symbol1, symbol2, symbol3))
        return _LoadStatementBuilder3(symbol1, symbol2, symbol3)
    }
}

class _LoadStatementBuilder1 internal constructor(
    val symbol1: String,
) {
    inline fun <reified S1> v(): S1 =
        _newReference(symbol1)
}


class _LoadStatementBuilder2 internal constructor(
    val symbol1: String,
    val symbol2: String,
) {
    inline fun <reified S1, reified S2> v(): Pair<S1, S2> =
        Pair(_newReference(symbol1), _newReference(symbol2))
}

class _LoadStatementBuilder3 internal constructor(
    val symbol1: String,
    val symbol2: String,
    val symbol3: String,
) {
    inline fun <reified S1, reified S2, reified S3> v(): Triple<S1, S2, S3> =
        Triple(_newReference(symbol1), _newReference(symbol2), _newReference(symbol3))
}

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified S> _newReference(name: String): S =
    when {
        S::class.java == Unit::class.java -> AnyReference("None")
        StringType::class.java.isAssignableFrom(S::class.java) -> StringReference(name)
        List::class.java.isAssignableFrom(S::class.java) -> ListReference<Any?>(name)
        Map::class.java.isAssignableFrom(S::class.java) -> DictionaryReference<Key, Value>(name)
        NumberType::class.java.isAssignableFrom(S::class.java) -> NumberReference(name)
        TupleType::class.java.isAssignableFrom(S::class.java) -> TupleReference(name)
        Comparable::class.java.isAssignableFrom(S::class.java) -> {
            val type = typeOf<S>().arguments.first().type
            when (type?.classifier as? KClass<*>) {
                Boolean::class -> BooleanReference(name)
                else -> AnyReference(name)
            }
        }
        else -> AnyReference(name)
    } as S

