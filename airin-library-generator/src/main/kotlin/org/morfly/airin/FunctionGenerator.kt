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

@file:Suppress("ObjectPropertyName")

package org.morfly.airin

import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.FunctionKind.Expression
import org.morfly.airin.starlark.lang.api.FunctionKind.Statement
import java.io.OutputStream


sealed class FunctionGenerator {

    protected val GeneratedFunction.builderName: String
        get() = when (kind) {
            Statement -> "registerFunctionCallStatement"
            Expression -> when (returnType) {
                DynamicType -> DYNAMIC_FUNC_BUILDER
                is SpecifiedType -> EXPR_FUNC_BUILDERS[returnType.actualType.qualifiedName]
                    ?: error("Invalid type ${returnType.qualifiedName}")
            }
        }

    abstract fun shouldGenerate(function: GeneratedFunction): Boolean

    abstract fun generate(file: OutputStream, function: GeneratedFunction)


    protected companion object {
        // indentation values for generated code
        const val indent4 = "    "
        const val indent8 = indent4 + indent4
        const val indent12 = indent8 + indent4

        private val EXPR_FUNC_BUILDERS = mapOf(
            StringType::class.qualifiedName to "stringFunctionCall",
            NumberType::class.qualifiedName to "numberFunctionCall",
            ListType::class.qualifiedName to "listFunctionCall",
            DictionaryType::class.qualifiedName to "dictFunctionCall",
            TupleType::class.qualifiedName to "tupleFunctionCall",
            BooleanBaseType::class.qualifiedName to "booleanFunctionCall",
        )

        private const val DYNAMIC_FUNC_BUILDER = "functionCallExpression"
    }
}
