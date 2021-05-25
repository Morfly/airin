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