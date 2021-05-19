package com.morlfy.airin.starlark.elements


/**
 *
 */
sealed class BazelFile(
    val name: String,
    val relativePath: String,
    val statements: List<Statement>
) : Element {

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}

/**
 *
 */
class WorkspaceFile(
    hasExtension: Boolean,
    statements: List<Statement>
) : BazelFile(
    name = if (hasExtension) "WORKSPACE.bazel" else "WORKSPACE",
    relativePath = "",
    statements
)

/**
 *
 */
class BuildFile(
    hasExtension: Boolean,
    relativePath: String,
    statements: List<Statement>
) : BazelFile(
    name = if (hasExtension) "BUILD.bazel" else "BUILD",
    relativePath,
    statements
)

/**
 *
 */
class StarlarkFile(
    name: String,
    relativePath: String,
    statements: List<Statement>
) : BazelFile(
    name = if (name.endsWith(".bzl", ignoreCase = true)) name else "$name.bzl",
    relativePath,
    statements
)

/**
 *
 */
class BazelRcFile(
    name: String = "",
    relativePath: String = "",
    statements: List<BazelRcStatement>
) : BazelFile(
    name = if (name.endsWith(".bazelrc", ignoreCase = true)) name else "$name.bazelrc",
    relativePath,
    statements
)