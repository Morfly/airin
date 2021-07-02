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
import org.morfly.airin.starlark.lang.api.FunctionKind.Expression
import org.morfly.airin.starlark.lang.api.FunctionKind.Statement
import org.morfly.airin.starlark.lang.api.FunctionScope.*
import org.morfly.airin.starlark.lang.api.LibraryFunction
import org.morfly.airin.starlark.lang.api.ReturnKind.Dynamic
import org.morfly.airin.starlark.lang.api.Returns


@LibraryFunction(
    name = "glob",
    scope = [Build, Workspace, Starlark],
    kind = Expression
)
private interface Glob {

    @Argument(vararg = true, required = true)
    val include: ListType<Label?>
    val exclude: ListType<Label?>?
    val exclude_directories: NumberType?
    val allow_empty: BooleanType?

    @Returns
    val returns: ListType<Label>
}


@LibraryFunction(
    name = "package",
    scope = [Build, Workspace],
    kind = Statement
)
private interface Package {

    val default_visibility: List<Label?>?
    val default_deprecation: StringType?
    val default_testonly: BooleanType?
    val features: List<StringType?>?
}


@LibraryFunction(
    name = "exports_files",
    scope = [Build, Workspace],
    kind = Statement
)
private interface ExportsFiles {

    @Argument(vararg = true, required = true)
    val exports_files: List<Label?>
    val visibility: List<Label>?
    val licences: List<StringType>?
}

@LibraryFunction(
    name = "select",
    scope = [Build, Workspace],
    kind = Expression
)
private interface Select {

    @Argument(required = true)
    val select: Map<Key, Value>
    val no_match_error: StringType?

    @Returns(kind = Dynamic)
    val returns: Any
}