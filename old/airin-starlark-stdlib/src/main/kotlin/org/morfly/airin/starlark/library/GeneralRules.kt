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

import org.morfly.airin.starlark.lang.BooleanType
import org.morfly.airin.starlark.lang.Label
import org.morfly.airin.starlark.lang.Name
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.api.Argument
import org.morfly.airin.starlark.lang.api.FunctionKind.Statement
import org.morfly.airin.starlark.lang.api.FunctionScope.Build
import org.morfly.airin.starlark.lang.api.LibraryFunction


@LibraryFunction(
    name = "alias",
    scope = [Build],
    kind = Statement
)
private interface Alias {

    @Argument(required = true)
    val name: Name

    @Argument(required = true)
    val actual: Label
}


@LibraryFunction(
    name = "genrule",
    scope = [Build],
    kind = Statement
)
private interface Genrule {

    @Argument(required = true)
    val name: Name
    val srcs: List<Label?>?
    val outs: List<StringType?>?
    val cmd: StringType?
    val cmd_bash: StringType?
    val cmd_bat: StringType?
    val cmd_ps: StringType?
    val exec_tools: List<Label?>?
    val executable: BooleanType?
    val local: BooleanType?
    val message: StringType?
    val output_licenses: List<StringType?>?
    val output_to_bindir: BooleanType?
    val tools: List<Label?>?
    val visibility: List<Label?>?
}