package com.morlfy.airin.starlark.elements


/**
 *
 */
sealed class BazelFile(
    val name: String,
    val relativePath: String,
    val statements: List<Statement>
) : Element {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
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
class BzlFile(
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
class StarFile(
    name: String,
    relativePath: String,
    statements: List<Statement>
) : BazelFile(
    name = if (name.endsWith(".star", ignoreCase = true)) name else "$name.star",
    relativePath,
    statements
)