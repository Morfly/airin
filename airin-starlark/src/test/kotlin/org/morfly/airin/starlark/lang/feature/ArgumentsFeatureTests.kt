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

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.morfly.airin.starlark.elements.*


class ArgumentsFeatureTests : FeatureSpec({

    feature("arguments feature") {

        scenario("string argument") {
            DynamicArgumentsFeatureUnderTest().apply {
                // given
                "string_arg" `=` "value"

                // assertions
                fargs.size shouldBe 1
                fargs.first().let { arg ->
                    arg.id shouldBe "string_arg"
                    arg.value.let { value ->
                        value.shouldBeTypeOf<StringLiteral>()
                        value.value shouldBe "value"
                    }
                }
            }
        }

        scenario("list argument") {
            DynamicArgumentsFeatureUnderTest().apply {
                // given
                "list_arg" `=` listOf("item1")

                // assertions
                fargs.size shouldBe 1
                fargs.first().let { arg ->
                    arg.id shouldBe "list_arg"
                    arg.value.let { value ->
                        value.shouldBeTypeOf<ListExpression<StringLiteral>>()
                        value.value.size shouldBe 1
                        value.value.first().let { item ->
                            item.shouldBeTypeOf<StringLiteral>()
                            item.value shouldBe "item1"
                        }
                    }
                }
            }
        }

        scenario("dictionary argument") {
            DynamicArgumentsFeatureUnderTest().apply {
                // given
                "dict_arg" `=` mapOf("key1" to "value1")

                // assertions
                fargs.size shouldBe 1
                fargs.first().let { arg ->
                    arg.id shouldBe "dict_arg"
                    arg.value.let { value ->
                        value.shouldBeTypeOf<DictionaryExpression>()
                        value.value.size shouldBe 1
                        value.value.entries.first().let { (key, value) ->
                            key.shouldBeTypeOf<StringLiteral>()
                            key.value shouldBe "key1"
                            value.shouldBeTypeOf<StringLiteral>()
                            value.value shouldBe "value1"
                        }
                    }
                }
            }
        }

        scenario("null argument") {
            DynamicArgumentsFeatureUnderTest().apply {
                // given
                "arg" `=` null

                // assertions
                fargs.size shouldBe 1
                fargs.first().let { arg ->
                    arg.id shouldBe "arg"
                    arg.value shouldBe NoneValue
                }
            }
        }
    }
})


private class DynamicArgumentsFeatureUnderTest : DynamicArgumentsFeature {
    override val fargs = linkedSetOf<Argument>()
}