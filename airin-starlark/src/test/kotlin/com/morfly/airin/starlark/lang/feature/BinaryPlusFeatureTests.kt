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
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.feature.BinaryPlusFeature
import com.morlfy.airin.starlark.lang.feature.CollectionsFeature
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf


class BinaryPlusFeatureTests : FeatureSpec({

    feature("binary plus feature") {

        scenario("string concatenation") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = "string1" `+` "string2"

                // assertions
                concat.shouldBeTypeOf<StringBinaryOperation>()
                concat.left shouldBe StringLiteral("string1")
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right shouldBe StringLiteral("string2")
            }
        }

        scenario("string concatenation with null") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = "string" `+` null

                // assertions
                concat.shouldBeTypeOf<StringBinaryOperation>()
                concat.left shouldBe StringLiteral("string")
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right shouldBe null
            }
        }

        scenario("string concatenation with null first") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = null `+` "string"

                // assertions
                concat.shouldBeTypeOf<StringBinaryOperation>()
                concat.left shouldBe null
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right shouldBe StringLiteral("string")

            }
        }
        scenario("list concatenation") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = list["item1"] `+` list["item2"]

                // assertions
                concat.shouldBeTypeOf<ListBinaryOperation<StringType>>()
                concat.left.let { left ->
                    left.shouldBeTypeOf<ListExpression<*>>()
                    left.value shouldBe listOf(StringLiteral("item1"))
                }
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right.let { right ->
                    right.shouldBeTypeOf<ListExpression<*>>()
                    right.value shouldBe listOf(StringLiteral("item2"))
                }
            }
        }

        scenario("list concatenation with null") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = list["item"] `+` null

                // assertions
                concat.shouldBeTypeOf<ListBinaryOperation<StringType>>()
                concat.left.let { left ->
                    left.shouldBeTypeOf<ListExpression<*>>()
                    left.value shouldBe listOf(StringLiteral("item"))
                }
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right shouldBe null
            }
        }

        scenario("list concatenation with null first") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = null `+` list["item2"]

                // assertions
                concat.shouldBeTypeOf<ListBinaryOperation<StringType>>()
                concat.left shouldBe null
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right.let { right ->
                    right.shouldBeTypeOf<ListExpression<*>>()
                    right.value shouldBe listOf(StringLiteral("item2"))
                }
            }
        }

        scenario("dictionary concatenation") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = dict { "key1" to "value1" } `+` dict { "key2" to "value2" }

                // assertions
                concat.shouldBeTypeOf<DictionaryBinaryOperation<StringType, StringType>>()
                concat.left.let { left ->
                    left.shouldBeTypeOf<DictionaryExpression>()
                    left.value.entries.size shouldBe 1
                    left.value.entries.first().let { (key, value) ->
                        key shouldBe StringLiteral("key1")
                        value.shouldBeTypeOf<DynamicValue>()
                        value.value shouldBe StringLiteral("value1")
                    }
                }
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right.let { right ->
                    right.shouldBeTypeOf<DictionaryExpression>()
                    right.value.entries.size shouldBe 1
                    right.value.entries.first().let { (key, value) ->
                        key shouldBe StringLiteral("key2")
                        value.shouldBeTypeOf<DynamicValue>()
                        value.value shouldBe StringLiteral("value2")
                    }
                }
            }
        }

        scenario("dictionary concatenation with null") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = dict { "key" to "value" } `+` null

                // assertions
                concat.shouldBeTypeOf<DictionaryBinaryOperation<StringType, StringType>>()
                concat.left.let { left ->
                    left.shouldBeTypeOf<DictionaryExpression>()
                    left.value.entries.size shouldBe 1
                    left.value.entries.first().let { (key, value) ->
                        key shouldBe StringLiteral("key")
                        value.shouldBeTypeOf<DynamicValue>()
                        value.value shouldBe StringLiteral("value")
                    }
                }
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right shouldBe null
            }
        }

        scenario("dictionary concatenation with null first in short expression form") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = null `+` { "key" to "value" }

                // assertions
                concat.shouldBeTypeOf<DictionaryBinaryOperation<StringType, StringType>>()
                concat.left shouldBe null
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right.let { right ->
                    right.shouldBeTypeOf<DictionaryExpression>()
                    right.value.entries.size shouldBe 1
                    right.value.entries.first().let { (key, value) ->
                        key shouldBe StringLiteral("key")
                        value.shouldBeTypeOf<DynamicValue>()
                        value.value shouldBe StringLiteral("value")
                    }
                }
            }
        }

        scenario("multiple string concatenations") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = "string1" `+` "string2" `+` "string3"

                // assertions
                concat.shouldBeTypeOf<StringBinaryOperation>()
                concat.left.let { left ->
                    left.shouldBeTypeOf<StringBinaryOperation>()
                    left.left shouldBe StringLiteral("string1")
                    left.operator shouldBe BinaryOperator.PLUS
                    left.right shouldBe StringLiteral("string2")
                }
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right shouldBe StringLiteral("string3")
            }
        }

        scenario("multiple list concatenations") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = list["item1"] `+` list["item2"] `+` list["item3"]

                // assertions
                concat.shouldBeTypeOf<ListBinaryOperation<StringType>>()
                concat.left.let { left ->
                    left.shouldBeTypeOf<ListBinaryOperation<StringType>>()
                    left.left.let { nestedLeft ->
                        nestedLeft.shouldBeTypeOf<ListExpression<*>>()
                        nestedLeft.value shouldBe listOf(StringLiteral("item1"))
                    }
                    left.operator shouldBe BinaryOperator.PLUS
                    left.right.let { nestedRight ->
                        nestedRight.shouldBeTypeOf<ListExpression<*>>()
                        nestedRight.value shouldBe listOf(StringLiteral("item2"))
                    }
                }
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right.let { right ->
                    right.shouldBeTypeOf<ListExpression<*>>()
                    right.value shouldBe listOf(StringLiteral("item3"))
                }
            }
        }

        scenario("multiple dictionary concatenations") {
            BinaryPlusFeatureUnderTest().apply {
                // given
                val concat = dict { "key1" to "value2" } `+` { "key2" to "value2" } `+` dict { "key3" to "value3" }

                // assertions
                concat.shouldBeTypeOf<DictionaryBinaryOperation<StringType, StringType>>()
                concat.left.let { left ->
                    left.shouldBeTypeOf<DictionaryBinaryOperation<StringType, StringType>>()
                    left.left.let { nestedLeft ->
                        nestedLeft.shouldBeTypeOf<DictionaryExpression>()
                        nestedLeft.value.entries.size shouldBe 1
                        nestedLeft.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key1")
                            value.shouldBeTypeOf<DynamicValue>()
                            value.value shouldBe StringLiteral("value2")
                        }
                    }
                    left.operator shouldBe BinaryOperator.PLUS
                    left.right.let { nestedRight ->
                        nestedRight.shouldBeTypeOf<DictionaryExpression>()
                        nestedRight.value.entries.size shouldBe 1
                        nestedRight.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key2")
                            value.shouldBeTypeOf<DynamicValue>()
                            value.value shouldBe StringLiteral("value2")
                        }
                    }
                }
                concat.operator shouldBe BinaryOperator.PLUS
                concat.right.let { right ->
                    right.shouldBeTypeOf<DictionaryExpression>()
                    right.value.entries.size shouldBe 1
                    right.value.entries.first().let { (key, value) ->
                        key shouldBe StringLiteral("key3")
                        value.shouldBeTypeOf<DynamicValue>()
                        value.value shouldBe StringLiteral("value3")
                    }
                }
            }
        }
    }
})


private class BinaryPlusFeatureUnderTest :
// Feature under test
    BinaryPlusFeature,
// Additional features for compatibility tests
    CollectionsFeature