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

@file:Suppress("PropertyName", "unused")

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.Argument
import org.morfly.airin.starlark.lang.api.FunctionKind.Statement
import org.morfly.airin.starlark.lang.api.FunctionScope.Build
import org.morfly.airin.starlark.lang.api.FunctionScope.Workspace
import org.morfly.airin.starlark.lang.api.LibraryFunction


@LibraryFunction(
    name = "http_archive",
    scope = [Build, Workspace],
    kind = Statement
)
private interface HttpArchive {

    @Argument(required = true)
    val name: Name
    val build_file: StringType?
    val build_file_content: StringType?
    val auth_patterns: Map<Key, Value>?
    val canonical_id: StringType?
    val netrc: StringType?
    val patch_args: List<StringType?>?
    val patch_cmds: List<StringType?>?
    val patch_cmds_win: List<StringType?>?
    val patch_tool: StringType?
    val patches: List<Label?>?
    val sha256: StringType?
    val strip_prefix: StringType?
    val type: StringType?
    val url: StringType?
    val urls: List<Label?>?
    val workspace_file: StringType?
    val workspace_file_content: StringType?
}


@LibraryFunction(
    name = "http_file",
    scope = [Build, Workspace],
    kind = Statement
)
private interface HttpFile {

    @Argument(required = true)
    val name: Name
    val auth_patterns: Map<Key, Value>?
    val canonical_id: StringType?
    val downloaded_file_path: StringType?
    val executable: BooleanType?
    val netrc: StringType?
    val sha256: StringType?
    val urls: List<Label?>?
}


@LibraryFunction(
    name = "http_jar",
    scope = [Build, Workspace],
    kind = Statement
)
private interface HttpJar {

    @Argument(required = true)
    val name: Name
    val auth_patterns: Map<Key, Value>?
    val canonical_id: StringType?
    val downloaded_file_path: StringType?
    val executable: BooleanType?
    val netrc: StringType?
    val sha256: StringType?
    val urls: List<Label?>?
}


@LibraryFunction(
    name = "git_repository",
    scope = [Build, Workspace],
    kind = Statement
)
private interface GitRepository {

    // TODO add args
}


@LibraryFunction(
    name = "new_git_repository",
    scope = [Build, Workspace],
    kind = Statement
)
private interface NewGitRepository {

    // TODO add args
}