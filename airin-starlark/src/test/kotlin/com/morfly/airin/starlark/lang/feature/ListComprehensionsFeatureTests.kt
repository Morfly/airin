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

@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.feature.ListComprehensionsFeature
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldBeTypeOf


class ListComprehensionsFeatureTests : FeatureSpec({

    feature("list comprehensions feature") {

        scenario("comprehension from 1 dimensional string list") {
            ListComprehensionsFeatureUnderTest().apply {
                // given
                val LIST = listOf("item1", "item2")

                val comprehension = "item" `in` LIST take { item -> item }

                // assertions
                comprehension.shouldBeTypeOf<ListComprehension<*>>()
                comprehension.body.shouldBeTypeOf<StringReference>()
                comprehension.body.name shouldBe "item"

                comprehension.clauses.size shouldBe 1
                comprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first() shouldBeSameInstanceAs comprehension.body

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListExpression>()
                        iterable.value.size shouldBe 2
                        iterable.value.first().let { item ->
                            item.shouldBeTypeOf<StringLiteral>()
                            item.value shouldBe "item1"
                        }
                    }
                }

            }
        }

        scenario("comprehension with condition from 1 dimensional string list") {
            ListComprehensionsFeatureUnderTest().apply {
                // given
                val LIST = listOf("item1", "item2")

                val comprehension = "item" `in` LIST `if` "condition" take { item -> item }

                // assertions
                comprehension.shouldBeTypeOf<ListComprehension<*>>()
                comprehension.body.shouldBeTypeOf<StringReference>()
                comprehension.body.name shouldBe "item"

                comprehension.clauses.size shouldBe 2
                comprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first() shouldBeSameInstanceAs comprehension.body

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListExpression>()
                        iterable.value.size shouldBe 2
                        iterable.value.first().let { item ->
                            item.shouldBeTypeOf<StringLiteral>()
                            item.value shouldBe "item1"
                        }
                    }
                }
                comprehension.clauses[1].let { ifClause ->
                    ifClause.shouldBeTypeOf<Comprehension.If>()
                    ifClause.condition.let { condition ->
                        condition.shouldBeTypeOf<AnyRawExpression>()
                        condition.value shouldBe "condition"
                    }
                }
            }
        }

        scenario("nested comprehension from 2D string list") {
            ListComprehensionsFeatureUnderTest().apply {
                // given
                val MATRIX = listOf(listOf("item1"), listOf("item2"))

                val comprehension = "sublist" `in` MATRIX `for` { sublist ->
                    "item" `in` sublist take { item -> item }
                }

                // assertions
                comprehension.shouldBeTypeOf<ListComprehension<*>>()
                comprehension.body.shouldBeTypeOf<StringReference>()
                comprehension.body.name shouldBe "item"

                comprehension.clauses.size shouldBe 2
                comprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first().let { sublist ->
                        sublist.shouldBeTypeOf<ListReference<*>>()
                        sublist.name shouldBe "sublist"
                    }
                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListExpression>()
                        iterable.value.size shouldBe 2
                        iterable.value.first().let { sublist ->
                            sublist.shouldBeTypeOf<ListExpression>()
                            sublist.value.size shouldBe 1
                        }
                    }
                }
                comprehension.clauses[1].let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first() shouldBeSameInstanceAs comprehension.body

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListReference<*>>()
                        iterable.name shouldBe "sublist"
                    }
                }
            }
        }

        scenario("nested comprehension with conditions from 2D string list") {
            ListComprehensionsFeatureUnderTest().apply {
                // given
                val MATRIX = listOf(listOf("item1"), listOf("item2"))

                val comprehension = "sublist" `in` MATRIX `if` "condition1" `for` { sublist ->
                    "item" `in` sublist `if` "condition2" take { item -> item }
                }

                // assertions
                comprehension.shouldBeTypeOf<ListComprehension<*>>()
                comprehension.body.shouldBeTypeOf<StringReference>()
                comprehension.body.name shouldBe "item"

                comprehension.clauses.size shouldBe 4
                comprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first().let { sublist ->
                        sublist.shouldBeTypeOf<ListReference<*>>()
                        sublist.name shouldBe "sublist"
                    }
                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListExpression>()
                        iterable.value.size shouldBe 2
                        iterable.value.first().let { sublist ->
                            sublist.shouldBeTypeOf<ListExpression>()
                            sublist.value.size shouldBe 1
                        }
                    }
                }
                comprehension.clauses[1].let { ifClause ->
                    ifClause.shouldBeTypeOf<Comprehension.If>()
                    ifClause.condition.let { condition ->
                        condition.shouldBeTypeOf<AnyRawExpression>()
                        condition.value shouldBe "condition1"
                    }
                }
                comprehension.clauses[2].let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first() shouldBeSameInstanceAs comprehension.body

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListReference<*>>()
                        iterable.name shouldBe "sublist"
                    }
                }
                comprehension.clauses[3].let { ifClause ->
                    ifClause.shouldBeTypeOf<Comprehension.If>()
                    ifClause.condition.let { condition ->
                        condition.shouldBeTypeOf<AnyRawExpression>()
                        condition.value shouldBe "condition2"
                    }
                }
            }
        }

        scenario("nested comprehension from 2D string list taking list values as items") {
            ListComprehensionsFeatureUnderTest().apply {
                // given
                val MATRIX = listOf(listOf("item1"), listOf("item2"))

                val comprehension = "sublist" `in` MATRIX `for` { sublist ->
                    "item" `in` sublist take { sublist }
                }

                // assertions
                comprehension.shouldBeTypeOf<ListComprehension<*>>()
                comprehension.body.shouldBeTypeOf<ListReference<StringType>>()
                comprehension.body.name shouldBe "sublist"

                comprehension.clauses.size shouldBe 2
                comprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first() shouldBeSameInstanceAs comprehension.body

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListExpression>()
                        iterable.value.size shouldBe 2
                        iterable.value.first().let { sublist ->
                            sublist.shouldBeTypeOf<ListExpression>()
                            sublist.value.size shouldBe 1
                        }
                    }
                }
                comprehension.clauses[1].let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first().let { item ->
                        item.shouldBeTypeOf<StringReference>()
                        item.name shouldBe "item"
                    }

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListReference<*>>()
                        iterable.name shouldBe "sublist"
                    }
                }
            }
        }

        scenario("nested comprehension from 3D string list") {
            ListComprehensionsFeatureUnderTest().apply {
                // given
                val MATRIX_3D = listOf(
                    listOf(listOf("item1"), listOf("item2")),
                    listOf(listOf("item3"), listOf("item4")),
                    listOf(listOf("item5"), listOf("item6"))
                )

                val comprehension = "sub_matrix" `in` MATRIX_3D `for` { sub_matrix ->
                    "sublist" `in` sub_matrix `for` { sublist ->
                        "item" `in` sublist take { item -> item }
                    }
                }

                // assertions
                comprehension.shouldBeTypeOf<ListComprehension<*>>()
                comprehension.body.shouldBeTypeOf<StringReference>()
                comprehension.body.name shouldBe "item"

                comprehension.clauses.size shouldBe 3
                comprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first().let { sub_matrix ->
                        sub_matrix.shouldBeTypeOf<ListReference<*>>()
                        sub_matrix.name shouldBe "sub_matrix"
                    }
                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListExpression>()
                        iterable.value.size shouldBe 3
                        iterable.value.first().let { sub_matrix ->
                            sub_matrix.shouldBeTypeOf<ListExpression>()
                            sub_matrix.value.size shouldBe 2
                        }
                    }
                }
                comprehension.clauses[1].let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first().let { sublist ->
                        sublist.shouldBeTypeOf<ListReference<*>>()
                        sublist.name shouldBe "sublist"
                    }
                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListReference<*>>()
                        iterable.name shouldBe "sub_matrix"
                    }
                }
                comprehension.clauses[2].let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first() shouldBeSameInstanceAs comprehension.body

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListReference<*>>()
                        iterable.name shouldBe "sublist"
                    }
                }
            }
        }

        scenario("nested comprehension from 3D string list taking list values as items") {
            ListComprehensionsFeatureUnderTest().apply {
                // given
                val MATRIX_3D = listOf(
                    listOf(listOf("item1"), listOf("item2")),
                    listOf(listOf("item3"), listOf("item4")),
                    listOf(listOf("item5"), listOf("item6"))
                )

                val comprehension = "sub_matrix" `in` MATRIX_3D `for` { sub_matrix ->
                    "sublist" `in` sub_matrix `for` { sublist ->
                        "item" `in` sublist take { sublist }
                    }
                }

                // assertions
                comprehension.shouldBeTypeOf<ListComprehension<*>>()
                comprehension.body.shouldBeTypeOf<ListReference<*>>()
                comprehension.body.name shouldBe "sublist"

                comprehension.clauses.size shouldBe 3
                comprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first().let { sub_matrix ->
                        sub_matrix.shouldBeTypeOf<ListReference<*>>()
                        sub_matrix.name shouldBe "sub_matrix"
                    }
                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListExpression>()
                        iterable.value.size shouldBe 3
                        iterable.value.first().let { sub_matrix ->
                            sub_matrix.shouldBeTypeOf<ListExpression>()
                            sub_matrix.value.size shouldBe 2
                        }
                    }
                }
                comprehension.clauses[1].let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first() shouldBeSameInstanceAs comprehension.body

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListReference<*>>()
                        iterable.name shouldBe "sub_matrix"
                    }
                }
                comprehension.clauses[2].let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first().let { item ->
                        item.shouldBeTypeOf<StringReference>()
                        item.name shouldBe "item"
                    }

                    forClause.iterable.let { iterable ->
                        iterable.shouldBeTypeOf<ListReference<*>>()
                        iterable.name shouldBe "sublist"
                    }
                }
            }
        }

        scenario("nested comprehension with taking another comprehension for each item") {
            ListComprehensionsFeatureUnderTest().apply {
                // given
                val LIST = listOf("item1", "item2", "item3", "item4")

                val comprehension = "i" `in` LIST take {
                    "j" `in` LIST take { j -> j }
                }

                // assertions
                comprehension.shouldBeTypeOf<ListComprehension<*>>()
                val nestedComprehension = comprehension.body
                nestedComprehension.shouldBeTypeOf<ListComprehension<*>>()
                nestedComprehension.body.shouldBeTypeOf<StringReference>()
                nestedComprehension.body.name shouldBe "j"

                comprehension.clauses.size shouldBe 1
                comprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first().let { i ->
                        i.shouldBeTypeOf<StringReference>()
                        i.name shouldBe "i"
                    }
                }

                nestedComprehension.clauses.size shouldBe 1
                nestedComprehension.clauses.first().let { forClause ->
                    forClause.shouldBeTypeOf<Comprehension.For>()
                    forClause.variables.size shouldBe 1
                    forClause.variables.first() shouldBeSameInstanceAs nestedComprehension.body
                }
            }
        }

    }
})


private class ListComprehensionsFeatureUnderTest :
// Feature under test
    ListComprehensionsFeature