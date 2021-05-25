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

package com.morlfy.airin.starlark.lang

import com.morlfy.airin.starlark.elements.WorkspaceFile


/**
 *
 */
class WorkspaceContext : StarlarkContext()

/**
 *
 */
inline fun WORKSPACE(body: WorkspaceContext.() -> Unit): WorkspaceFile =
    WorkspaceContext()
        .apply(body)
        .let { WorkspaceFile(hasExtension = false, statements = it.statements.toList()) }

/**
 *
 */
object WORKSPACE

/**
 *
 */
inline fun WORKSPACE.bazel(body: WorkspaceContext.() -> Unit): WorkspaceFile =
    WorkspaceContext()
        .apply(body)
        .let { WorkspaceFile(hasExtension = true, statements = it.statements.toList()) }