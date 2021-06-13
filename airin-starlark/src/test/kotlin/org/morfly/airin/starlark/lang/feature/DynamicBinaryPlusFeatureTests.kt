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
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf


class DynamicBinaryPlusFeatureTests : FeatureSpec({

    feature("dynamic binary plus feature") {

        scenario("string concatenation") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val string1 = _StringValueAccumulator(DynamicValue(StringLiteral("string1")))
                val concat = string1 `+` "string2"

                // assertions
                concat.shouldBeTypeOf<_StringValueAccumulator>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<StringBinaryOperation>()
                    it.left shouldBe StringLiteral("string1")
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right shouldBe StringLiteral("string2")
                }
            }
        }

        scenario("string concatenation with null") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val string1 = _StringValueAccumulator(DynamicValue(StringLiteral("string1")))
                val concat = string1 `+` null

                // assertions
                concat.shouldBeTypeOf<_StringValueAccumulator>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<StringBinaryOperation>()
                    it.left shouldBe StringLiteral("string1")
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right shouldBe NoneValue
                }
            }
        }

        scenario("list concatenation") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val list1 = _ListValueAccumulator<StringType>(DynamicValue(ListExpression(listOf("item1"))))
                val concat = list1 `+` list["item2"]

                // assertions
                concat.shouldBeTypeOf<_ListValueAccumulator<StringType>>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<ListBinaryOperation<StringType>>()
                    it.left.let { left ->
                        left.shouldBeTypeOf<ListExpression<*>>()
                        left.value shouldBe listOf(StringLiteral("item1"))
                    }
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right.let { right ->
                        right.shouldBeTypeOf<ListExpression<*>>()
                        right.value shouldBe listOf(StringLiteral("item2"))
                    }
                }
            }
        }

        scenario("list concatenation with null") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val list1 = _ListValueAccumulator<StringType>(DynamicValue(ListExpression(listOf("item1"))))
                val concat = list1 `+` null

                // assertions
                concat.shouldBeTypeOf<_ListValueAccumulator<StringType>>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<ListBinaryOperation<StringType>>()
                    it.left.let { left ->
                        left.shouldBeTypeOf<ListExpression<*>>()
                        left.value shouldBe listOf(StringLiteral("item1"))
                    }
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right shouldBe NoneValue
                }
            }
        }

        scenario("dictionary concatenation") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val dict1 =
                    _DictionaryValueAccumulator<Key, Value>(DynamicValue(DictionaryExpression(mapOf("key1" to "value1"))))
                val concat = dict1 `+` dict { "key2" to "value2" }

                // assertions
                concat.shouldBeTypeOf<_DictionaryValueAccumulator<Key, Value>>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<DictionaryBinaryOperation<Key, Value>>()
                    it.left.let { left ->
                        left.shouldBeTypeOf<DictionaryExpression>()
                        left.value.entries.size shouldBe 1
                        left.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key1")
                            value shouldBe StringLiteral("value1")
                        }
                    }
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right.let { right ->
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
        }

        scenario("dictionary concatenation in short expression form") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val dict1 =
                    _DictionaryValueAccumulator<Key, Value>(DynamicValue(DictionaryExpression(mapOf("key1" to "value1"))))
                val concat = dict1 `+` { "key2" to "value2" }

                // assertions
                concat.shouldBeTypeOf<_DictionaryValueAccumulator<Key, Value>>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<DictionaryBinaryOperation<Key, Value>>()
                    it.left.let { left ->
                        left.shouldBeTypeOf<DictionaryExpression>()
                        left.value.entries.size shouldBe 1
                        left.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key1")
                            value shouldBe StringLiteral("value1")
                        }
                    }
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right.let { right ->
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
        }

        scenario("dictionary concatenation with null") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val dict1 =
                    _DictionaryValueAccumulator<Key, Value>(DynamicValue(DictionaryExpression(mapOf("key1" to "value1"))))
                val concat = dict1 `+` null

                // assertions
                concat.shouldBeTypeOf<_DictionaryValueAccumulator<Key, Value>>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<DictionaryBinaryOperation<Key, Value>>()
                    it.left.let { left ->
                        left.shouldBeTypeOf<DictionaryExpression>()
                        left.value.entries.size shouldBe 1
                        left.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key1")
                            value shouldBe StringLiteral("value1")
                        }
                    }
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right shouldBe NoneValue
                }
            }
        }

        scenario("multiple string concatenation") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val string1 = _StringValueAccumulator(DynamicValue(StringLiteral("string1")))
                val concat = string1 `+` "string2" `+` "string3"

                // assertions
                concat.shouldBeTypeOf<_StringValueAccumulator>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<StringBinaryOperation>()
                    it.left.let { left ->
                        left.shouldBeTypeOf<StringBinaryOperation>()
                        left.left shouldBe StringLiteral("string1")
                        left.operator shouldBe BinaryOperator.PLUS
                        left.right shouldBe StringLiteral("string2")
                    }
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right shouldBe StringLiteral("string3")
                }
            }
        }

        scenario("multiple list concatenation") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val list1 = _ListValueAccumulator<StringType>(DynamicValue(ListExpression(listOf("item1"))))
                val concat = list1 `+` list["item2"] `+` list["item3"]

                // assertions
                concat.shouldBeTypeOf<_ListValueAccumulator<StringType>>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<ListBinaryOperation<StringType>>()
                    it.left.let { left ->
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
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right.let { right ->
                        right.shouldBeTypeOf<ListExpression<*>>()
                        right.value shouldBe listOf(StringLiteral("item3"))
                    }
                }
            }
        }

        scenario("multiple dictionary concatenation") {
            DynamicBinaryPlusFeatureUnderTest().apply {
                // given
                val dict1 =
                    _DictionaryValueAccumulator<Key, Value>(DynamicValue(DictionaryExpression(mapOf("key1" to "value1"))))
                val concat = dict1 `+` { "key2" to "value2" } `+` dict { "key3" to "value3" }

                // assertions
                concat.shouldBeTypeOf<_DictionaryValueAccumulator<Key, Value>>()
                concat.holder.shouldBeTypeOf<DynamicValue>()
                concat.holder.value.let {
                    it.shouldBeTypeOf<DictionaryBinaryOperation<Key, Value>>()
                    it.left.let { left ->
                        left.shouldBeTypeOf<DictionaryBinaryOperation<Key, Value>>()
                        left.left.let { nestedLeft ->
                            nestedLeft.shouldBeTypeOf<DictionaryExpression>()
                            nestedLeft.value.entries.size shouldBe 1
                            nestedLeft.value.entries.first().let { (key, value) ->
                                key shouldBe StringLiteral("key1")
                                value shouldBe StringLiteral("value1")
                            }
                        }
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
                    it.operator shouldBe BinaryOperator.PLUS
                    it.right.let { right ->
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
    }
})


private class DynamicBinaryPlusFeatureUnderTest :
// Feature under test
    DynamicBinaryPlusFeature,
// Additional features for compatibility tests
    CollectionsFeature