package com.morfly.airin.starlark.elements

import com.morlfy.airin.starlark.elements.BuildFile
import com.morlfy.airin.starlark.elements.WorkspaceFile
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class BazelFilesTests : ShouldSpec({

    context("WORKSPACE file") {
        should("'hasExtension = false' create name without extension") {
            val workspace = WorkspaceFile(
                hasExtension = false,
                statements = emptyList()
            )

            workspace.name shouldBe "WORKSPACE"
        }

        should("'hasExtension = true' create name with extension") {
            val workspace = WorkspaceFile(
                hasExtension = true,
                statements = emptyList()
            )

            workspace.name shouldBe "WORKSPACE.bazel"
        }

        should("have correct 'relativePath'") {
            val workspace = WorkspaceFile(
                hasExtension = true,
                statements = emptyList()
            )

            workspace.relativePath shouldBe ""
        }
    }

    context("BUILD file") {
        should("'hasExtension = false' create name without extension") {
            val workspace = BuildFile(
                hasExtension = false,
                relativePath = "",
                statements = emptyList()
            )

            workspace.name shouldBe "BUILD"
        }

        should("'hasExtension = true' create name with extension") {
            val workspace = BuildFile(
                hasExtension = true,
                relativePath = "",
                statements = emptyList()
            )

            workspace.name shouldBe "BUILD.bazel"
        }

        should("have correct 'relativePath'") {
            val workspace = BuildFile(
                hasExtension = true,
                relativePath = "relative/path",
                statements = emptyList()
            )

            workspace.relativePath shouldBe "relative/path"
        }
    }

    // TODO StarlarkFile
})
