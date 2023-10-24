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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldBeTypeOf


class AssignmentsFeatureTests : FeatureSpec({

    feature("assignments feature") {

        feature("property delegation") {

            scenario("string variable usage (should not register new statements)") {
                AssignmentsFeatureUnderTest().apply {
                    // given
                    val STRING_VARIABLE by "value"

                    // assertions
                    statements.size shouldBe 1

                    val statement = statements.first()

                    val REF = STRING_VARIABLE
                    statements.size shouldBe 1
                    statement shouldBeSameInstanceAs statements.first()
                }
            }

            scenario("list variable usage (should not register new statements)") {
                AssignmentsFeatureUnderTest().apply {
                    // given
                    val LIST_VARIABLE by listOf("item1")

                    // assertions
                    statements.size shouldBe 1

                    val statement = statements.first()

                    val REF = LIST_VARIABLE
                    statements.size shouldBe 1
                    statement shouldBeSameInstanceAs statements.first()
                }
            }

            scenario("dictionary variable usage (should not register new statements)") {
                AssignmentsFeatureUnderTest().apply {
                    // given
                    val DICT_VARIABLE by mapOf("key" to "value")

                    // assertions
                    statements.size shouldBe 1

                    val statement = statements.first()

                    val REF = DICT_VARIABLE
                    statements.size shouldBe 1
                    statement shouldBeSameInstanceAs statements.first()
                }
            }
        }

        scenario("string literal assignment") {
            AssignmentsFeatureUnderTest().apply {
                // given
                val STRING_VARIABLE by "value"

                // assertions
                statements.size shouldBe 1

                val statement = statements.first()
                statement.shouldBeTypeOf<Assignment>()

                statement.name shouldBe "STRING_VARIABLE"
                statement.value?.let {
                    it.shouldBeTypeOf<StringLiteral>()
                    it.value shouldBe "value"
                }
            }
        }

        scenario("new list assignment") {
            AssignmentsFeatureUnderTest().apply {
                // given
                val LIST_VARIABLE by listOf("item1")

                // assertions
                statements.size shouldBe 1

                val statement = statements.first()
                statement.shouldBeTypeOf<Assignment>()

                statement.name shouldBe "LIST_VARIABLE"
                statement.value!!.let { list ->
                    list.shouldBeTypeOf<ListExpression<*>>()
                    list.value.size shouldBe 1

                    list.value.first().let { item ->
                        item.shouldBeTypeOf<StringLiteral>()
                        item.value shouldBe "item1"
                    }
                }
            }
        }

        scenario("new dictionary assignment") {
            AssignmentsFeatureUnderTest().apply {
                // given
                val DICT_VARIABLE by mapOf("key" to 1)

                // assertions
                statements.size shouldBe 1

                val statement = statements.first()
                statement.shouldBeTypeOf<Assignment>()

                statement.name shouldBe "DICT_VARIABLE"
                statement.value!!.let { dict ->
                    dict.shouldBeTypeOf<DictionaryExpression>()
                    dict.value.size shouldBe 1

                    dict.value.entries.first().let { (key, value) ->
                        key.shouldBeTypeOf<StringLiteral>()
                        key.value shouldBe "key"

                        value.shouldBeTypeOf<IntegerLiteral>()
                        value.value shouldBe 1
                    }
                }
            }
        }
    }


})

private class AssignmentsFeatureUnderTest : AssignmentsFeature {
    override val statements = mutableListOf<Statement>()
}