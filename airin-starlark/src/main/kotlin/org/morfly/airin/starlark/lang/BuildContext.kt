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

@file:Suppress("FunctionName")

package org.morfly.airin.starlark.lang

import org.morfly.airin.starlark.elements.BuildFile
import org.morfly.airin.starlark.lang.api.LanguageScope
import org.morfly.airin.starlark.lang.api.BuildExpressionsLibrary
import org.morfly.airin.starlark.lang.api.BuildStatementsLibrary


/**
 * Starlark language context that is specific to Bazel BUILD files.
 */
@LanguageScope
class BuildContext : CommonStarlarkContext<BuildContext>(),
    BuildStatementsLibrary, BuildExpressionsLibrary {

    override fun newContext() = BuildContext()
}

/**
 * Builder function that allows entering Starlark template engine context and use Kotlin DSL
 */
inline fun BUILD(relativePath: String = "", body: BuildContext.() -> Unit): BuildFile =
    BuildContext()
        .apply(body)
        .let { BuildFile(hasExtension = false, relativePath, statements = it.statements.toList()) }

/**
 *
 */
object BUILD

/**
 *
 */
inline fun BUILD.bazel(relativePath: String = "", body: BuildContext.() -> Unit): BuildFile =
    BuildContext()
        .apply(body)
        .let { BuildFile(hasExtension = true, relativePath, statements = it.statements.toList()) }