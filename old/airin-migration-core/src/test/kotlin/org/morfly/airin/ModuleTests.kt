package org.morfly.airin

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.morfly.airin.BazelLabelFormat.*
import org.morfly.airin.BazelLabelFormat.Short


class ModuleTests : ShouldSpec({
    val module = Module(relativePath = "org/airin/test", "test-target", null)
    val module1 = Module(relativePath = "org/airin/test", "test", null)
    val module2 = Module(relativePath = "test", "test", null)

    context("bazelLabel function") {
        should("return label in Full format") {
            module.bazelLabel(format = Full) shouldBe "//org/airin/test:test_target"
            module1.bazelLabel(format = Full) shouldBe "//org/airin/test:test"
            module2.bazelLabel(format = Full) shouldBe "//test:test"
        }

        should("return label in FullOmitsTarget format") {
            module.bazelLabel(format = FullOmitsTarget) shouldBe "//org/airin/test:test_target"
            module1.bazelLabel(format = FullOmitsTarget) shouldBe "//org/airin/test"
            module2.bazelLabel(format = FullOmitsTarget) shouldBe "//test"
        }

        should("return label in FullRepo format") {
            module.bazelLabel(format = FullRepo("my_repo")) shouldBe "@my_repo//org/airin/test:test_target"
            module1.bazelLabel(format = FullRepo("my_repo")) shouldBe "@my_repo//org/airin/test:test"
            module2.bazelLabel(format = FullRepo("my_repo")) shouldBe "@my_repo//test:test"
        }

        should("return label in FullRepoOmitsTarget format") {
            module.bazelLabel(format = FullRepoOmitsTarget("my_repo")) shouldBe "@my_repo//org/airin/test:test_target"
            module1.bazelLabel(format = FullRepoOmitsTarget("my_repo")) shouldBe "@my_repo//org/airin/test"
            module2.bazelLabel(format = FullRepoOmitsTarget("my_repo")) shouldBe "@my_repo//test"
        }

        should("return label in ShortColon format") {
            module.bazelLabel(format = ShortColon) shouldBe ":test_target"
            module1.bazelLabel(format = ShortColon) shouldBe ":test"
            module2.bazelLabel(format = ShortColon) shouldBe ":test"
        }

        should("return label in Short format") {
            module.bazelLabel(format = Short) shouldBe "test_target"
            module1.bazelLabel(format = Short) shouldBe "test"
            module2.bazelLabel(format = Short) shouldBe "test"
        }
    }
})