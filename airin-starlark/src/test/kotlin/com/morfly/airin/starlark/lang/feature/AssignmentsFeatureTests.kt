@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.feature.AssignmentsFeature
import com.morlfy.airin.starlark.lang.feature.ReassignmentsFeature
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldBeTypeOf


class AssignmentsFeatureTests : FeatureSpec({

    feature("assignments feature") {

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
                    list.shouldBeTypeOf<ListExpression>()
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

    feature("reassignments feature") {

        scenario("string literal reassignment") {
            ReassignmentsFeatureUnderTest().apply {
                // given
                val STRING_VARIABLE by "value"
                STRING_VARIABLE `=` "new_variable"

                // assertions
                statements.size shouldBe 2

                val statement = statements[1]
                statement.shouldBeTypeOf<Assignment>()

                statement.name shouldBe "STRING_VARIABLE"
                statement.value?.let {
                    it.shouldBeTypeOf<StringLiteral>()
                    it.value shouldBe "new_variable"
                }
            }
        }

        scenario("new list reassignment") {
            ReassignmentsFeatureUnderTest().apply {
                // given
                val LIST_VARIABLE by listOf("item1")
                LIST_VARIABLE `=` listOf("new_item")

                // assertions
                statements.size shouldBe 2

                val statement = statements[1]
                statement.shouldBeTypeOf<Assignment>()

                statement.name shouldBe "LIST_VARIABLE"
                statement.value!!.let { list ->
                    list.shouldBeTypeOf<ListExpression>()
                    list.value.size shouldBe 1

                    list.value.first().let { item ->
                        item.shouldBeTypeOf<StringLiteral>()
                        item.value shouldBe "new_item"
                    }
                }
            }
        }

        scenario("new dictionary reassignment") {
            ReassignmentsFeatureUnderTest().apply {
                // given
                val DICT_VARIABLE by mapOf("key" to 1)
                DICT_VARIABLE `=` mapOf("new_key" to "new_value")

                // assertions
                statements.size shouldBe 2

                val statement = statements[1]
                statement.shouldBeTypeOf<Assignment>()

                statement.name shouldBe "DICT_VARIABLE"
                statement.value!!.let { dict ->
                    dict.shouldBeTypeOf<DictionaryExpression>()
                    dict.value.size shouldBe 1

                    dict.value.entries.first().let { (key, value) ->
                        key.shouldBeTypeOf<StringLiteral>()
                        key.value shouldBe "new_key"

                        value.shouldBeTypeOf<StringLiteral>()
                        value.value shouldBe "new_value"
                    }
                }
            }
        }
    }

    feature("assignment by delegation (implementation)") {
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

        scenario("dictionary variable usage does not register new statements") {
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
})

private class AssignmentsFeatureUnderTest : AssignmentsFeature {
    override val statements = mutableListOf<Statement>()
}

private class ReassignmentsFeatureUnderTest : AssignmentsFeature, ReassignmentsFeature {
    override val statements = mutableListOf<Statement>()
}