package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.feature.BinaryPlusFeature
import com.morlfy.airin.starlark.lang.feature.CollectionsFeature
import com.morlfy.airin.starlark.lang.feature.DynamicBinaryPlusFeature
import com.morlfy.airin.starlark.lang.feature.MappingFeature
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
                    value.shouldBeTypeOf<DynamicValue>()
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
                    value.shouldBeTypeOf<DynamicValue>()
                    value.value.let { listExpr ->
                        listExpr.shouldBeTypeOf<ListExpression>()
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
                    value.shouldBeTypeOf<DynamicValue>()
                    value.value.let { dictExpr ->
                        dictExpr.shouldBeTypeOf<DictionaryExpression>()
                        dictExpr.value.entries.size shouldBe 1
                        dictExpr.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key2")
                            value.shouldBeTypeOf<DynamicValue>()
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
                    value.shouldBeTypeOf<DynamicValue>()
                    value.value.let { dictExpr ->
                        dictExpr.shouldBeTypeOf<DictionaryExpression>()
                        dictExpr.value.entries.size shouldBe 1
                        dictExpr.value.entries.first().let { (key, value) ->
                            key shouldBe StringLiteral("key2")
                            value.shouldBeTypeOf<DynamicValue>()
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

                    value.shouldBeTypeOf<DynamicValue>()
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

                    value.shouldBeTypeOf<DynamicValue>()
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
    override val kwargs = mutableMapOf<Expression?, Expression?>()
}