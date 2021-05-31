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
import org.morfly.airin.starlark.lang.StringType
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf


class CollectionsFeatureTests : FeatureSpec({

    feature("collections feature") {

        feature("lists") {

            scenario("list expression with square brackets") {
                CollectionsFeatureUnderTest().apply {
                    // given
                    val list: List<StringType> = list["item1", "item2"]

                    // assertions
                    list.shouldBeTypeOf<ListExpression<*>>()
                    list.value.let {
                        it.size shouldBe 2
                        it.first() shouldBe StringLiteral("item1")
                    }
                }
            }

            scenario("list expression with round brackets") {
                CollectionsFeatureUnderTest().apply {
                    // given
                    val list: List<StringType> = list("item1", "item2")

                    // assertions
                    list.shouldBeTypeOf<ListExpression<*>>()
                    list.value.let {
                        it.size shouldBe 2
                        it.first() shouldBe StringLiteral("item1")
                    }
                }
            }

            scenario("empty typed list expression with round brackets") {
                CollectionsFeatureUnderTest().apply {
                    // given
                    val list: List<StringType> = list()

                    // assertions
                    list.shouldBeTypeOf<ListExpression<*>>()
                    list.value.shouldBeEmpty()
                }
            }

            scenario("empty list expression with round brackets") {
                CollectionsFeatureUnderTest().apply {
                    // given
                    val list = list()

                    // assertions
                    list.shouldBeTypeOf<ListExpression<*>>()
                    list.value.shouldBeEmpty()
                }
            }
        }

        feature("dictionaries") {

            scenario("dict expression with curly brackets") {
                CollectionsFeatureUnderTest().apply {
                    // given
                    val dict = dict { "key1" to "value2"; "key2" to "value2" }

                    // assertions
                    dict.shouldBeTypeOf<DictionaryExpression>()
                    dict.value.let {
                        it.size shouldBe 2
                        val (key, value) = it.entries.first()
                        key shouldBe StringLiteral("key1")
                        value.shouldBeTypeOf<DynamicValue>()
                    }
                }
            }

            scenario("empty dict expression with curly brackets") {
                CollectionsFeatureUnderTest().apply {
                    // given
                    val dict = dict {}

                    // assertions
                    dict.shouldBeTypeOf<DictionaryExpression>()
                    dict.value.shouldBeEmpty()
                }
            }

        }

        feature("tuples") {

            scenario("tuple expression") {
                CollectionsFeatureUnderTest().apply {
                    // given
                    val tuple = tuple("1", 2, 3.0)

                    // assertions
                    tuple.shouldBeTypeOf<TupleExpression<*>>()
                    tuple.value.size shouldBe 3
                    tuple.value.first() shouldBe StringLiteral("1")
                    tuple.value[1] shouldBe IntegerLiteral(2)
                    tuple.value.last() shouldBe FloatLiteral(3.0)
                }
            }

            scenario("empty tuple expression") {
                CollectionsFeatureUnderTest().apply {
                    // given
                    val tuple = tuple()

                    // assertions
                    tuple.shouldBeTypeOf<TupleExpression<*>>()
                    tuple.value.shouldBeEmpty()
                }
            }
        }
    }
})


private class CollectionsFeatureUnderTest :
// Feature under test
    CollectionsFeature