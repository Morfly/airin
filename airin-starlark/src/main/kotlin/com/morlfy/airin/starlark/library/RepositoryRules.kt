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

@file:Suppress("PropertyName", "SpellCheckingInspection")

package com.morlfy.airin.starlark.library

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.*
import com.morlfy.airin.starlark.lang.feature.FunctionCallContext
import com.morlfy.airin.starlark.lang.feature.registerFunctionCallStatement


// ===== http_archive =====

/**
 *
 */
fun ConfigurationContext.http_archive(
    name: Name,
    build_file: StringType? = UnspecifiedString,
    build_file_content: StringType? = UnspecifiedString,
    auth_patterns: Map<Key, Value>? = UnspecifiedDictionary,
    canonical_id: StringType? = UnspecifiedString,
    netrc: StringType? = UnspecifiedString,
    patch_args: List<StringType?>? = UnspecifiedList,
    patch_cmds: List<StringType?>? = UnspecifiedList,
    patch_cmds_win: List<StringType?>? = UnspecifiedList,
    patch_tool: StringType? = UnspecifiedString,
    patches: List<Label?>? = UnspecifiedList,
    sha256: StringType? = UnspecifiedString,
    strip_prefix: StringType? = UnspecifiedString,
    type: StringType? = UnspecifiedString,
    url: StringType? = UnspecifiedString,
    urls: List<Label?>? = UnspecifiedList,
    workspace_file: StringType? = UnspecifiedString,
    workspace_file_content: StringType? = UnspecifiedString,
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (build_file != UnspecifiedString) it += Argument("build_file", Expression(build_file, ::StringLiteral))
        if (build_file_content != UnspecifiedString)
            it += Argument("build_file_content", Expression(build_file_content, ::StringLiteral))
        if (auth_patterns != UnspecifiedDictionary)
            it += Argument("auth_patterns", Expression(auth_patterns, ::DictionaryExpression))
        if (canonical_id != UnspecifiedString) it += Argument("canonical_id", Expression(canonical_id, ::StringLiteral))
        if (netrc != UnspecifiedString) it += Argument("netrc", Expression(netrc, ::StringLiteral))
        if (patch_args != UnspecifiedList) it += Argument("patch_args", Expression(patch_args, ::ListExpression))
        if (patch_cmds != UnspecifiedList) it += Argument("patch_cmds", Expression(patch_cmds, ::ListExpression))
        if (patch_cmds_win != UnspecifiedList)
            it += Argument("patch_cmds_win", Expression(patch_cmds_win, ::ListExpression))
        if (patch_tool != UnspecifiedString) it += Argument("patch_tool", Expression(patch_tool, ::StringLiteral))
        if (patches != UnspecifiedList) it += Argument("patches", Expression(patches, ::ListExpression))
        if (sha256 != UnspecifiedString) it += Argument("sha256", Expression(sha256, ::StringLiteral))
        if (strip_prefix != UnspecifiedString) it += Argument("strip_prefix", Expression(strip_prefix, ::StringLiteral))
        if (type != UnspecifiedString) it += Argument("type", Expression(type, ::StringLiteral))
        if (url != UnspecifiedString) it += Argument("url", Expression(url, ::StringLiteral))
        if (urls != UnspecifiedList) it += Argument("urls", Expression(urls, ::ListExpression))
        if (workspace_file != UnspecifiedString)
            it += Argument("workspace_file", Expression(workspace_file, ::StringLiteral))
        if (workspace_file_content != UnspecifiedString)
            it += Argument("workspace_file_content", Expression(workspace_file_content, ::StringLiteral))
    }
    registerFunctionCallStatement("http_archive", args)
}

/**
 *
 */
fun ConfigurationContext.http_archive(body: HttpArchiveContext.() -> Unit) =
    registerFunctionCallStatement("http_archive", HttpArchiveContext(), body)

/**
 *
 */
class HttpArchiveContext : FunctionCallContext() {
    var name: Name by fargs
    var build_file: StringType? by fargs
    var build_file_content: StringType? by fargs
    var auth_patterns: Map<Key, Value>? by fargs
    var canonical_id: StringType? by fargs
    var netrc: StringType? by fargs
    var patch_args: List<StringType?>? by fargs
    var patch_cmds: List<StringType?>? by fargs
    var patch_cmds_win: List<StringType?>? by fargs
    var patch_tool: StringType? by fargs
    var patches: List<Label?>? by fargs
    var sha256: StringType? by fargs
    var strip_prefix: StringType? by fargs
    var type: StringType? by fargs
    var url: StringType? by fargs
    var urls: List<Label?>? by fargs
    var workspace_file: StringType? by fargs
    var workspace_file_content: StringType? by fargs
}

// ===== http_file =====

/**
 *
 */
fun ConfigurationContext.http_file(
    name: Name,
    auth_patterns: Map<Key, Value>? = UnspecifiedDictionary,
    canonical_id: StringType? = UnspecifiedString,
    downloaded_file_path: StringType? = UnspecifiedString,
    executable: BooleanType? = UnspecifiedBoolean,
    netrc: StringType? = UnspecifiedString,
    sha256: StringType? = UnspecifiedString,
    urls: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (auth_patterns != UnspecifiedDictionary)
            it += Argument("auth_patterns", Expression(auth_patterns, ::DictionaryExpression))
        if (canonical_id != UnspecifiedString) it += Argument("canoncal_id", Expression(canonical_id, ::StringLiteral))
        if (downloaded_file_path != UnspecifiedString)
            it += Argument("downloaded_file_path", Expression(downloaded_file_path, ::StringLiteral))
        if (executable != UnspecifiedBoolean) it += Argument("executable", Expression(executable, ::BooleanLiteral))
        if (netrc != UnspecifiedString) it += Argument("netrc", Expression(netrc, ::StringLiteral))
        if (sha256 != UnspecifiedString) it += Argument("sha256", Expression(sha256, ::StringLiteral))
        if (urls != UnspecifiedList) it += Argument("urls", Expression(urls, ::ListExpression))
    }
    registerFunctionCallStatement("http_file", args)
}

/**
 *
 */
fun ConfigurationContext.http_file(body: HttpFileContext.() -> Unit) =
    registerFunctionCallStatement("http_file", HttpFileContext(), body)

/**
 *
 */
class HttpFileContext : FunctionCallContext() {
    var name: Name by fargs
    var auth_patterns: Map<Key, Value>? by fargs
    var canonical_id: StringType? by fargs
    var downloaded_file_path: StringType? by fargs
    var executable: BooleanType? by fargs
    var netrc: StringType? by fargs
    var sha256: StringType? by fargs
    var urls: List<Label?>? by fargs
}

// ===== http_jar =====

fun ConfigurationContext.http_jar(
    name: Name,
    auth_patterns: Map<Key, Value>? = UnspecifiedDictionary,
    canonical_id: StringType? = UnspecifiedString,
    netrc: StringType? = UnspecifiedString,
    sha256: StringType? = UnspecifiedString,
    url: List<Label?>? = UnspecifiedList,
    urls: List<Label?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (auth_patterns != UnspecifiedDictionary)
            it += Argument("auth_patterns", Expression(auth_patterns, ::DictionaryExpression))
        if (canonical_id != UnspecifiedString) it += Argument("canonical_id", Expression(canonical_id, ::StringLiteral))
        if (netrc != UnspecifiedString) it += Argument("netrc", Expression(netrc, ::StringLiteral))
        if (sha256 != UnspecifiedString) it += Argument("sha256", Expression(sha256, ::StringLiteral))
        if (url != UnspecifiedList) it += Argument("url", Expression(url, ::ListExpression))
        if (urls != UnspecifiedList) it += Argument("urls", Expression(urls, ::ListExpression))
    }
    registerFunctionCallStatement("http_jar", args)
}

/**
 *
 */
fun ConfigurationContext.http_jar(body: HttpJarContext.() -> Unit) =
    registerFunctionCallStatement("http_jar", HttpJarContext(), body)

/**
 *
 */
class HttpJarContext : FunctionCallContext() {
    var name: Name by fargs
    var auth_patterns: Map<Key, Value>? by fargs
    var canonical_id: StringType? by fargs
    var netrc: StringType? by fargs
    var sha256: StringType? by fargs
    var url: List<Label?>? by fargs
    var urls: List<Label?>? by fargs
}

// ===== git_repository =====

/**
 *
 */
fun ConfigurationContext.git_repository() {
    TODO()
}

/**
 *
 */
fun ConfigurationContext.git_repository(body: GitRepositoryContext.() -> Unit) =
    registerFunctionCallStatement("git_repository", GitRepositoryContext(), body)

/**
 *
 */
class GitRepositoryContext : FunctionCallContext() {
    init {
        TODO()
    }
}

// ===== new_git_repository =====

/**
 *
 */
fun ConfigurationContext.new_git_repository() {
    TODO()
}

/**
 *
 */
fun ConfigurationContext.new_git_repository(body: NewGitRepositoryContext.() -> Unit) =
    registerFunctionCallStatement("new_git_repository", NewGitRepositoryContext(), body)

/**
 *
 */
class NewGitRepositoryContext : FunctionCallContext() {
    init {
        TODO()
    }
}