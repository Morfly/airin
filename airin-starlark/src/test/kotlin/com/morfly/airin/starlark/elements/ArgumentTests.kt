package com.morfly.airin.starlark.elements

import com.morlfy.airin.starlark.elements.Argument
import com.morlfy.airin.starlark.elements.Arguments
import com.morlfy.airin.starlark.elements.StringLiteral
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe


class ArgumentTests : ShouldSpec({

    context("'Argument' element") {

        should("two 'Argument' objects with same 'id' but different 'value' properties be equal") {
            val arg1 = Argument(id = "id", value = StringLiteral("value1"))
            val arg2 = Argument(id = "id", value = StringLiteral("value2"))
            val set = hashSetOf(arg1, arg2)

            arg1 shouldBe arg2
            set.size shouldBe 1
        }

        should("two 'Argument' objects with different 'id' but same 'value' properties be NOT equal") {
            val value = StringLiteral("value")
            val arg1 = Argument(id = "id1", value)
            val arg2 = Argument(id = "id2", value)
            val set = hashSetOf(arg1, arg2)

            arg1 shouldNotBe arg2
            set.size shouldBe 2
        }

        should("'Arguments' factory function create set of 'Argument' objects") {
            val set = Arguments(
                mapOf(
                    "string" to "",
                    "string" to "duplicate",
                    "int" to 1,
                    "another_int" to 1
                )
            )

            set.size shouldBe 3
        }
    }
})