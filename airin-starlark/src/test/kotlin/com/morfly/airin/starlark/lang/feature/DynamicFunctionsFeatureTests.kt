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

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.feature.CollectionsFeature
import com.morlfy.airin.starlark.lang.feature.DynamicFunctionsFeature
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf


class DynamicFunctionsFeatureTests : FeatureSpec({

    feature("dynamic functions feature") {

        scenario("function call with various arguments") {
            DynamicFunctionsFeatureUnderTest().apply {
                // given
                "function" {
                    "arg1" `=` "value"
                    "arg2" `=` list["item"]
                    "arg3" `=` { "key" to "value" }
                    "arg4" `=` dict { "key" to "value" }
                }

                // assertions
                statements.size shouldBe 1
                statements.first().let {
                    it.shouldBeTypeOf<ExpressionStatement>()
                    it.expression.let { func ->
                        func.shouldBeTypeOf<AnyFunctionCall>()
                        func.name shouldBe "function"
                        func.args.size shouldBe 4
                        val args = func.args.toList()
                        args[0].let { arg ->
                            arg.id shouldBe "arg1"
                            arg.value shouldBe StringLiteral("value")
                        }
                        args[1].let { arg ->
                            arg.id shouldBe "arg2"
                            arg.value.shouldBeTypeOf<ListExpression<*>>()
                        }
                        args[2].let { arg ->
                            arg.id shouldBe "arg3"
                            arg.value.shouldBeTypeOf<DictionaryExpression>()
                        }
                        args[3].let { arg ->
                            arg.id shouldBe "arg4"
                            arg.value.shouldBeTypeOf<DictionaryExpression>()
                        }
                    }
                }
            }
        }

        scenario("function call with no arguments (1)") {
            DynamicFunctionsFeatureUnderTest().apply {
                // given
                "function"{}

                // assertions
                statements.size shouldBe 1
                statements.first().let {
                    it.shouldBeTypeOf<ExpressionStatement>()
                    it.expression.let { func ->
                        func.shouldBeTypeOf<AnyFunctionCall>()
                        func.name shouldBe "function"
                        func.args.shouldBeEmpty()
                    }
                }
            }
        }

        scenario("function call with not arguments (2)") {
            DynamicFunctionsFeatureUnderTest().apply {
                // given
                "function"()

                // assertions
                statements.size shouldBe 1
                statements.first().let {
                    it.shouldBeTypeOf<ExpressionStatement>()
                    it.expression.let { func ->
                        func.shouldBeTypeOf<AnyFunctionCall>()
                        func.name shouldBe "function"
                        func.args.shouldBeEmpty()
                    }
                }
            }
        }
    }
})


private class DynamicFunctionsFeatureUnderTest :
// feature under test
    DynamicFunctionsFeature,
// Additional features for compatibility tests
    CollectionsFeature {

    override val statements = mutableListOf<Statement>()
}