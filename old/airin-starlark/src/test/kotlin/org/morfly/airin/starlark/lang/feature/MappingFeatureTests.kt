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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf


class MappingFeatureTests : FeatureSpec({

    feature("mapping feature") {

        scenario("string value") {
            MappingFeatureUnderTest().apply {
                // given
                "key" to "value"

                // assertions
                kwargs.size shouldBe 1
                kwargs.entries.first().let { (key, value) ->
                    key shouldBe StringLiteral("key")
                    value.shouldBeTypeOf<DynamicExpression>()
                    value.value shouldBe StringLiteral("value")
                }
            }
        }

        scenario("list value") {
            MappingFeatureUnderTest().apply {
                // given
                "key" to list["item"]

                // assertions
                kwargs.size shouldBe 1
                kwargs.entries.first().let { (key, value) ->
                    key shouldBe StringLiteral("key")
                    value.shouldBeTypeOf<DynamicExpression>()
                    value.value.let { listExpr ->
                        listExpr.shouldBeTypeOf<ListExpression<*>>()
                        listExpr.value shouldBe listOf(StringLiteral("item"))
                    }
                }
            }
        }

        scenario("dictionary value") {
            MappingFeatureUnderTest().apply {
                // given
                "key1" to dict { "key2" to "value" }

                // assertions
                kwargs.size shouldBe 1
                kwargs.entries.first().let { (key, value) ->
                    key shouldBe StringLiteral("key1")
                    value.shouldBeTypeOf<DynamicExpression>()
                    value.value.let { dictExpr ->
                        dictExpr.shouldBeTypeOf<DictionaryExpression>()
                        dictExpr.value.entries.size shouldBe 1
                        dictExpr.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key2")
                            value.shouldBeTypeOf<DynamicExpression>()
                            value.value shouldBe StringLiteral("value")
                        }
                    }
                }
            }
        }

        scenario("dictionary value short expression form") {
            MappingFeatureUnderTest().apply {
                // given
                "key1" to { "key2" to "value" }

                // assertions
                kwargs.size shouldBe 1
                kwargs.entries.first().let { (key, value) ->
                    key shouldBe StringLiteral("key1")
                    value.shouldBeTypeOf<DynamicExpression>()
                    value.value.let { dictExpr ->
                        dictExpr.shouldBeTypeOf<DictionaryExpression>()
                        dictExpr.value.entries.size shouldBe 1
                        dictExpr.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key2")
                            value.shouldBeTypeOf<DynamicExpression>()
                            value.value shouldBe StringLiteral("value")
                        }
                    }
                }
            }
        }

        scenario("concatenated key") {
            MappingFeatureUnderTest().apply {
                // given
                "key" `+` "1" to "value"

                // assertions
                kwargs.size shouldBe 1
                kwargs.entries.first().let { (key, value) ->
                    key.shouldBeTypeOf<StringBinaryOperation>()
                    key.left shouldBe StringLiteral("key")
                    key.operator shouldBe BinaryOperator.PLUS
                    key.right shouldBe StringLiteral("1")

                    value.shouldBeTypeOf<DynamicExpression>()
                    value.value shouldBe StringLiteral("value")
                }
            }
        }

        scenario("concatenated value") {
            MappingFeatureUnderTest().apply {
                // given
                "key" to "value" `+` "1"

                // assertions
                kwargs.size shouldBe 1
                kwargs.entries.first().let { (key, value) ->
                    key shouldBe StringLiteral("key")

                    value.shouldBeTypeOf<DynamicExpression>()
                    value.value.let {
                        it.shouldBeTypeOf<StringBinaryOperation>()
                        it.left shouldBe StringLiteral("value")
                        it.operator shouldBe BinaryOperator.PLUS
                        it.right shouldBe StringLiteral("1")
                    }
                }
            }
        }
    }
})


private class MappingFeatureUnderTest :
// Feature under test
    MappingFeature,
// Additional features for compatibility tests
    CollectionsFeature,
    BinaryPlusFeature,
    DynamicBinaryPlusFeature {
    override val kwargs = mutableMapOf<Expression, Expression>()
}