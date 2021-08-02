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
import org.morfly.airin.starlark.lang.api.BracketsKind.Round
import org.morfly.airin.starlark.lang.api.FunctionKind.Expression
import org.morfly.airin.starlark.lang.api.FunctionKind.Statement
import org.morfly.airin.starlark.lang.api.FunctionScope.*
import org.morfly.airin.starlark.lang.api.LibraryFunction
import org.morfly.airin.starlark.lang.api.Returns


@LibraryFunction(
    name = "maven_install",
    scope = [Build, Workspace],
    kind = Statement
)
private interface MavenInstall {

    val name: StringType?
    val artifacts: List<StringType?>?
    val repositories: List<StringType?>?
    val fail_on_missing_checksum: BooleanType?
    val fetch_sources: BooleanType?
    val excluded_artifacts: List<StringType?>?
    val override_targets: Map<Key, Value>?
    val generate_compat_repositories: BooleanType?
    val strict_visibility: BooleanType?
    val jetify: BooleanType?
    val jetify_include_list: List<StringType?>?
}


@LibraryFunction(
    name = "artifact",
    scope = [Build, Workspace, Starlark],
    kind = Expression,
    brackets = [Round]
)
private interface Artifact {

    @Argument(underlyingName = "", required = true)
    val artifact: StringType

    @Returns
    val returns: Label
}