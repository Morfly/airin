package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.LoadStatement
import com.morlfy.airin.starlark.elements.Statement
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.lang.feature.LoadStatementsFeature
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf


class LoadStatementsFeatureTests : FeatureSpec({

    feature("load statements feature") {

        scenario("load statement with symbols") {
            LoadStatementsFeatureUnderTest().apply {
                // given
                load("file.bzl", "symbol_1", "symbol_2")

                // assertions
                statements.size shouldBe 1
                statements.first().let {
                    it.shouldBeTypeOf<LoadStatement>()
                    it.file shouldBe StringLiteral("file.bzl")
                    it.symbols.size shouldBe 2
                    it.symbols.first().let { symbol1 ->
                        symbol1.shouldBeTypeOf<LoadStatement.Symbol>()
                        symbol1.name shouldBe StringLiteral("symbol_1")
                        symbol1.alias shouldBe null
                    }
                    it.symbols[1].let { symbol2 ->
                        symbol2.shouldBeTypeOf<LoadStatement.Symbol>()
                        symbol2.name shouldBe StringLiteral("symbol_2")
                        symbol2.alias shouldBe null
                    }
                }
            }
        }

        scenario("load statement with no symbols") {
            LoadStatementsFeatureUnderTest().apply {
                // given
                load("file.bzl")

                // assertions
                statements.size shouldBe 1
                statements.first().let {
                    it.shouldBeTypeOf<LoadStatement>()
                    it.file shouldBe StringLiteral("file.bzl")
                    it.symbols.shouldBeEmpty()
                }
            }
        }
    }
})


private class LoadStatementsFeatureUnderTest :
// Feature under test
    LoadStatementsFeature {

    override val statements = mutableListOf<Statement>()
}