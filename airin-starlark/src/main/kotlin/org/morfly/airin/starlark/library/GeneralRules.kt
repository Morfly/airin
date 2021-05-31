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

@file:Suppress("PropertyName")

package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.feature.FunctionCallContext
import org.morfly.airin.starlark.lang.feature.registerFunctionCallStatement


// ===== alias =====

/**
 *
 */
fun BuildContext.alias(
    name: Name,
    actual: Label
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        it += Argument("actual", Expression(actual, ::StringLiteral))
    }
    registerFunctionCallStatement(name = "alias", args)
}

/**
 *
 */
fun BuildContext.alias(body: AliasContext.() -> Unit) {
    registerFunctionCallStatement("alias", AliasContext(), body)
}

/**
 *
 */
class AliasContext : FunctionCallContext() {
    var name: Name by fargs
    var alias: Label by fargs
}

// ===== genrule =====

/**
 *
 */
fun BuildContext.genrule(
    name: Name,
    srcs: List<Label?>? = UnspecifiedList,
    outs: List<StringType?>? = UnspecifiedList,
    cmd: StringType? = UnspecifiedString,
    cmd_bash: StringType? = UnspecifiedString,
    cmd_bat: StringType? = UnspecifiedString,
    cmd_ps: StringType? = UnspecifiedString,
    exec_tools: List<Label?>? = UnspecifiedList,
    executable: BooleanType? = UnspecifiedBoolean,
    local: BooleanType? = UnspecifiedBoolean,
    message: StringType? = UnspecifiedString,
    output_licenses: List<StringType?>? = UnspecifiedList,
    output_to_bindir: BooleanType? = UnspecifiedBoolean,
    tools: List<Label?>? = UnspecifiedList,
    visibility: List<Label?>? = UnspecifiedList,
) {
    val args = linkedSetOf<Argument>().also {
        it += Argument("name", Expression(name, ::StringLiteral))
        if (srcs !== UnspecifiedList) it += Argument("srcs", Expression(srcs, ::ListExpression))
        if (outs !== UnspecifiedList) it += Argument("outs", Expression(outs, ::ListExpression))
        if (cmd !== UnspecifiedString) it += Argument("cmd", Expression(cmd, ::StringLiteral))
        if (cmd_bash !== UnspecifiedString) it += Argument("cmd_bash", Expression(cmd_bash, ::StringLiteral))
        if (cmd_bat !== UnspecifiedString) it += Argument("cmd_bat", Expression(cmd_bat, ::StringLiteral))
        if (cmd_ps !== UnspecifiedString) it += Argument("cmd_ps", Expression(cmd_ps, ::StringLiteral))
        if (exec_tools !== UnspecifiedList) it += Argument("exec_tools", Expression(exec_tools, ::ListExpression))
        if (executable !== UnspecifiedBoolean) it += Argument("executable", Expression(executable, ::BooleanLiteral))
        if (local !== UnspecifiedBoolean) it += Argument("local", Expression(local, ::BooleanLiteral))
        if (message !== UnspecifiedString) it += Argument("message", Expression(message, ::StringLiteral))
        if (output_licenses !== UnspecifiedList) it += Argument(
            "output_licenses", Expression(output_licenses, ::ListExpression)
        )
        if (output_to_bindir !== UnspecifiedBoolean) it += Argument(
            "output_to_bindir", Expression(output_to_bindir, ::BooleanLiteral)
        )
        if (tools !== UnspecifiedList) it += Argument("tools", Expression(tools, ::ListExpression))
        if (visibility !== UnspecifiedList) it += Argument("visibility", Expression(visibility, ::ListExpression))
    }
    registerFunctionCallStatement(name = "genrule", args)
}

/**
 *
 */
fun BuildContext.genrule(body: GenruleContext.() -> Unit) {
    registerFunctionCallStatement("genrule", GenruleContext(), body)
}

/**
 *
 */
class GenruleContext : FunctionCallContext() {
    var name: Name by fargs
    var srcs: List<Label?>? by fargs
    var outs: List<StringType?>? by fargs
    var cmd: StringType? by fargs
    var cmd_bash: StringType? by fargs
    var cmd_bat: StringType? by fargs
    var cmd_ps: StringType? by fargs
    var exec_tools: List<Label?>? by fargs
    var executable: BooleanType? by fargs
    var local: BooleanType? by fargs
    var message: StringType? by fargs
    var output_licenses: List<StringType?>? by fargs
    var output_to_bindir: BooleanType? by fargs
    var tools: List<Label?>? by fargs
    var visibility: List<Label?>? by fargs
}