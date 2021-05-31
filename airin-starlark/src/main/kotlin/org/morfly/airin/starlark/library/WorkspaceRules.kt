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

@file:Suppress("PropertyName", "FunctionName")

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.feature.FunctionCallContext
import org.morfly.airin.starlark.lang.feature.registerFunctionCallStatement


// ===== bind =====

/**
 *
 */
fun WorkspaceContext.bind(
    name: Name,
    actual: Label? = UnspecifiedString,
    visibility: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (actual !== UnspecifiedString)
            it += Argument("actual", Expression(actual, ::StringLiteral))
        if (visibility !== UnspecifiedList)
            it += Argument("visibility", Expression(visibility, ::ListExpression))
    }
    registerFunctionCallStatement("bind", args)
}

/**
 *
 */
fun WorkspaceContext.bind(body: BindContext.() -> Unit) =
    registerFunctionCallStatement("bind", BindContext(), body)

/**
 *
 */
class BindContext : FunctionCallContext() {
    var name: Name by fargs
    var actual: Label? by fargs
    var visibility: List<Label?>? by fargs
}

// ===== local_repository =====

/**
 *
 */
fun WorkspaceContext.local_repository(
    name: Name,
    path: StringType,
    repo_mapping: Map<Key, Value>? = UnspecifiedDictionary
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        it += Argument("path", Expression(path, ::StringLiteral))
        if (repo_mapping !== UnspecifiedDictionary)
            it += Argument("repo_mapping", Expression(repo_mapping, ::DictionaryExpression))
    }
    registerFunctionCallStatement("local_repository", args)
}

/**
 *
 */
fun WorkspaceContext.local_repository(body: LocalRepositoryContext.() -> Unit) =
    registerFunctionCallStatement("local_repository", LocalRepositoryContext(), body)

/**
 *
 */
class LocalRepositoryContext : FunctionCallContext() {
    var name: Name by fargs
    var path: StringType by fargs
    var repo_mapping: Map<Key, Value>? by fargs
}

// ===== new_local_repository =====

/**
 *
 */
fun WorkspaceContext.new_local_repository(
    name: Name,
    build_file: StringType? = UnspecifiedString,
    build_file_content: StringType? = UnspecifiedString,
    path: StringType? = UnspecifiedString,
    repo_mapping: Map<Key, Value>? = UnspecifiedDictionary,
    workspace_file: StringType? = UnspecifiedString,
    workspace_file_content: StringType? = UnspecifiedString
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (build_file !== UnspecifiedString) it += Argument(
            "build_file",
            Expression(build_file, ::StringLiteral)
        )
        if (build_file_content !== UnspecifiedString)
            it += Argument("build_file_content", Expression(build_file_content, ::StringLiteral))
        if (path !== UnspecifiedString) it += Argument("path", Expression(path, ::StringLiteral))
        if (repo_mapping !== UnspecifiedDictionary)
            it += Argument("repo_mapping", Expression(repo_mapping, ::DictionaryExpression))
        if (workspace_file !== UnspecifiedString)
            it += Argument("workspace_file", Expression(workspace_file, ::StringLiteral))
        if (workspace_file_content !== UnspecifiedString)
            it += Argument(
                "workspace_file_content",
                Expression(workspace_file_content, ::StringLiteral)
            )
    }
    registerFunctionCallStatement("new_local_repository", args)
}

/**
 *
 */
fun WorkspaceContext.new_local_repository(body: NewLocalRepositoryContext.() -> Unit) =
    registerFunctionCallStatement("new_local_repository", NewLocalRepositoryContext(), body)

/**
 *
 */
class NewLocalRepositoryContext : FunctionCallContext() {
    var name: Name by fargs
    var build_file: StringType? by fargs
    var build_file_content: StringType? by fargs
    var path: StringType? by fargs
    var repo_mapping: Map<Key, Value>? by fargs
    var workspace_file: StringType? by fargs
    var workspace_file_content: StringType? by fargs
}