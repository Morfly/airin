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

import org.morfly.airin.starlark.elements.LoadStatement
import org.morfly.airin.starlark.elements.Statement
import org.morfly.airin.starlark.elements.StringLiteral
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