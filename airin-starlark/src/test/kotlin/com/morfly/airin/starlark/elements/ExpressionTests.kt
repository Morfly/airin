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

package com.morfly.airin.starlark.elements

import com.morlfy.airin.starlark.elements.*
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldBeTypeOf


class ExpressionTests : ShouldSpec({

    context("'Expression' factory function") {
        should("return null for null argument") {
            Expression(null) shouldBe null
        }

        should("return same instance for expression argument") {
            val expression: Expression = StringReference(name = "NAME")

            Expression(expression) shouldBeSameInstanceAs expression
        }

        should("create 'StringLiteral' object for string argument") {
            Expression("string").shouldBeTypeOf<StringLiteral>()
        }

        should("create 'BooleanLiteral' object for boolean argument") {
            Expression(true).shouldBeTypeOf<BooleanLiteral>()
        }

        should("create 'ListExpression' object for list argument") {
            Expression(listOf("item")).shouldBeTypeOf<ListExpression>()
        }

        should("create 'DictionaryExpression' object for map argument") {
            Expression(mapOf("key" to "value")).shouldBeTypeOf<DictionaryExpression>()
        }

        should("create 'IntegerExpression' object for integer arguments") {
            Expression(1).shouldBeTypeOf<IntegerLiteral>()
            Expression(1L).shouldBeTypeOf<IntegerLiteral>()
            Expression(1.toShort()).shouldBeTypeOf<IntegerLiteral>()
            Expression(1.toByte()).shouldBeTypeOf<IntegerLiteral>()
        }

        should("create 'FloatExpression' object for floating-point arguments") {
            Expression(1.5).shouldBeTypeOf<FloatLiteral>()
            Expression(1.5f).shouldBeTypeOf<FloatLiteral>()
        }

        should("create 'StringLiteral' object for any other argument") {
            val arg = Any()
            val result = Expression(arg)

            result.shouldBeTypeOf<StringLiteral>()
            result.value shouldBe arg.toString()
        }
    }
})