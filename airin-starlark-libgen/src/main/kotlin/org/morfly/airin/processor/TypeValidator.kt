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

package org.morfly.airin.processor

import org.morfly.airin.starlark.lang.*


private typealias QualifiedName = String


interface TypeValidator {

    val allowedTypes: Set<QualifiedName?>

    val allowedTypeArguments: Map<QualifiedName?, Set<QualifiedName?>>

    fun validate(type: QualifiedName?, typeArgument: QualifiedName? = null): Boolean
}

class TypeValidatorImpl : TypeValidator {

    private val voidType = Unit::class.qualifiedName

    override val allowedTypes = setOf(
        StringType::class.qualifiedName,
        NumberType::class.qualifiedName,
        ListType::class.qualifiedName,
        DictionaryType::class.qualifiedName,
        TupleType::class.qualifiedName,
        BooleanBaseType::class.qualifiedName,
        Any::class.qualifiedName,
        voidType
    )

    override val allowedTypeArguments = mapOf(
        ListType::class.qualifiedName to allowedTypes,
        DictionaryType::class.qualifiedName to setOf(
            BaseKey::class.qualifiedName,
            BaseValue::class.qualifiedName
        )
    )

    override fun validate(type: QualifiedName?, typeArgument: QualifiedName?): Boolean =
        if (typeArgument != null)
            typeArgument in allowedTypeArguments[type] ?: emptySet()
        else type in allowedTypes
}