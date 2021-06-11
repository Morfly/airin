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

@file:Suppress("PropertyName", "FunctionName", "SpellCheckingInspection")

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.feature.FunctionCallContext
import org.morfly.airin.starlark.lang.feature.registerFunctionCallStatement
import org.morfly.airin.starlark.lang.feature.stringFunctionCall


// ===== maven_install =====

/**
 * maven_install Bazel rule.
 */
fun ConfigurationContext<*>.maven_install(
    artifacts: List<StringType?>? = UnspecifiedList,
    repositories: List<StringType?>? = UnspecifiedList,
    fail_on_missing_checksum: BooleanType? = UnspecifiedBoolean,
    fetch_sources: BooleanType? = UnspecifiedBoolean,
    excluded_artifacts: List<StringType?>? = UnspecifiedList,
    override_targets: Map<Key, Value>? = UnspecifiedDictionary,
    generate_compat_repositories: BooleanType? = UnspecifiedBoolean,
    strict_visibility: BooleanType? = UnspecifiedBoolean,
    jetify: BooleanType? = UnspecifiedBoolean,
    jetify_include_list: List<StringType?>? = UnspecifiedList
) {
    val args = linkedSetOf<Argument>().also {
        if (artifacts !== UnspecifiedList) it += Argument("artifacts", Expression(artifacts, ::ListExpression))
        if (repositories !== UnspecifiedList)
            it += Argument("repositories", Expression(repositories, ::ListExpression))
        if (fail_on_missing_checksum !== UnspecifiedBoolean)
            it += Argument("fail_on_missing_checksum", Expression(fail_on_missing_checksum, ::BooleanLiteral))
        if (fetch_sources !== UnspecifiedBoolean)
            it += Argument("fetch_sources", Expression(fetch_sources, ::BooleanLiteral))
        if (excluded_artifacts !== UnspecifiedList)
            it += Argument("excluded_artifacts", Expression(excluded_artifacts, ::ListExpression))
        if (override_targets !== UnspecifiedDictionary)
            it += Argument("override_targets", Expression(override_targets, ::DictionaryExpression))
        if (generate_compat_repositories !== UnspecifiedBoolean)
            it += Argument("generate_compat_repositories", Expression(generate_compat_repositories, ::BooleanLiteral))
        if (strict_visibility !== UnspecifiedBoolean)
            it += Argument("strict_visibility", Expression(strict_visibility, ::BooleanLiteral))
        if (jetify !== UnspecifiedBoolean) it += Argument("jetify", Expression(jetify, ::BooleanLiteral))
        if (jetify_include_list !== UnspecifiedList)
            it += Argument("jetify_include_list", Expression(jetify_include_list, ::ListExpression))
    }
    registerFunctionCallStatement("maven_install", args)
}

/**
 * maven_install Bazel rule.
 */
fun ConfigurationContext<*>.maven_install(body: MavenInstallContext.() -> Unit) =
    registerFunctionCallStatement("maven_install", MavenInstallContext(), body)

class MavenInstallContext : FunctionCallContext() {
    var artifacts: List<StringType?>? by fargs
    var repositories: List<StringType?>? by fargs
    var fail_on_missing_checksum: BooleanType? by fargs
    var fetch_sources: BooleanType? by fargs
    var excluded_artifacts: List<StringType?>? by fargs
    var override_targets: Map<Key, Value>? by fargs
    var generate_compat_repositories: BooleanType? by fargs
    var strict_visibility: BooleanType? by fargs
    var jetify: BooleanType? by fargs
    var jetify_include_list: List<StringType?>? by fargs
}

// ===== artifact =====

/**
 * artifact Bazel function.
 */
fun ConfigurationContext<*>.artifact(a: StringType): StringType =
    stringFunctionCall(
        name = "artifact",
        args = mapOf(
            "" to a
        )
    )