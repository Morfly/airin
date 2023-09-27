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

import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.Label
import org.morfly.airin.starlark.lang.Name
import org.morfly.airin.starlark.lang.Value
import org.morfly.airin.starlark.lang.api.Argument
import org.morfly.airin.starlark.lang.api.FunctionKind.Statement
import org.morfly.airin.starlark.lang.api.FunctionScope.Workspace
import org.morfly.airin.starlark.lang.api.LibraryFunction


@LibraryFunction(
    name = "register_toolchains",
    scope = [Workspace],
    kind = Statement
)
private interface RegisterToolchains {

    // TODO introduce Starlark analog of vararg
    @Argument(underlyingName = "", required = true)
    val toolchain: Label
}


@LibraryFunction(
    name = "workspace",
    scope = [Workspace],
    kind = Statement
)
interface WorkspaceFunction {

    @Argument(required = true)
    val name: Name
    val managed_directories: Map<Key, Value>?
}