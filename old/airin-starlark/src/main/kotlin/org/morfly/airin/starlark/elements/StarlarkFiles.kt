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

package org.morfly.airin.starlark.elements


/**
 * Abstract element that represents the root of a syntax tree for a Starlark file, such as BUILD, WORKSPACE or .bzl.
 *
 * @param name the file name.
 * @param relativePath the path of the file in relation to the project root directory.
 * @param statements the list of children elements in the tree.
 */
sealed class StarlarkFile(
    val name: String,
    val relativePath: String,
    val statements: List<Statement>
) : Element {

    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 * The root of a syntax tree for a Bazel WORKSPACE file.
 *
 * @param hasExtension defines whether the WORKSPACE file should have .bazel extension.
 */
class WorkspaceFile(
    hasExtension: Boolean,
    statements: List<Statement>
) : StarlarkFile(
    name = if (hasExtension) "WORKSPACE.bazel" else "WORKSPACE",
    relativePath = "",
    statements
)

/**
 * The root of a syntax tree for a Bazel BUILD file.
 *
 * @param hasExtension defines whether the BUILD file should have .bazel extension.
 */
class BuildFile(
    hasExtension: Boolean,
    relativePath: String,
    statements: List<Statement>
) : StarlarkFile(
    name = if (hasExtension) "BUILD.bazel" else "BUILD",
    relativePath,
    statements
)

/**
 * The root of a syntax tree for a .bzl file.
 */
class BzlFile(
    name: String,
    relativePath: String,
    statements: List<Statement>
) : StarlarkFile(
    name = if (name.endsWith(".bzl", ignoreCase = true)) name else "$name.bzl",
    relativePath,
    statements
)

/**
 * The root of a syntax tree for a .star file.
 */
class StarFile(
    name: String,
    relativePath: String,
    statements: List<Statement>
) : StarlarkFile(
    name = if (name.endsWith(".star", ignoreCase = true)) name else "$name.star",
    relativePath,
    statements
)