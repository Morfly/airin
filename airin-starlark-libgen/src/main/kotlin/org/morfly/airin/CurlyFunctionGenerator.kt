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

package org.morfly.airin

import com.google.devtools.ksp.processing.KSPLogger
import org.morfly.airin.starlark.lang.api.BracketsKind
import java.io.OutputStream
import kotlin.reflect.KClass


class CurlyFunctionGenerator(
    private val scopeResolver: FunctionScopeResolver,
    private val logger: KSPLogger
) : FunctionGenerator() {

    override fun shouldGenerate(function: GeneratedFunction): Boolean =
        BracketsKind.Curly in function.brackets

    override fun generate(file: OutputStream, function: GeneratedFunction) {
        val ctxClassName = generateContext(file, function)

        file += "\n"

        val scopeClasses = scopeResolver.resolve(function.scope, function.kind)
        for (cls in scopeClasses) {
            generate(file, function, cls, ctxClassName)
            file += "\n\n"
        }
    }

    private fun generate(file: OutputStream, function: GeneratedFunction, scopeClass: KClass<*>, ctxClassName: String) {
        val funSlot = when (function.returnType) {
            is SpecifiedType -> "fun"
            DynamicType -> "inline fun <reified T>"
        }
        val returnTypeSlot = when (val type = function.returnType) {
            is SpecifiedType -> ": ${type.fullName}"
            DynamicType -> ": T"
        }

        file += "$funSlot ${scopeClass.simpleName}.`${function.shortName}`(body: $ctxClassName.() -> Unit)$returnTypeSlot ="
        file += "\n"

        val functionBuilderName = function.builderName
        file += "${indent4}$functionBuilderName(\"${function.shortName}\", $ctxClassName(), body)"
    }

    /**
     * @return name of the context class
     */
    private fun generateContext(file: OutputStream, function: GeneratedFunction): String {
        val ctxClassName = function.annotatedClassName + "Context"
        val allArguments = mutableListOf<Arg>().also {
            if (function.vararg != null && function.vararg.shortName.isNotBlank())
                it += function.vararg.toArgument()
            it += function.arguments.filter { arg -> arg.shortName.isNotBlank() }
        }

        file += "class $ctxClassName : FunctionCallContext() {\n"
        for (arg in allArguments) {
            file += indent4
            file += "var ${arg.fullName}: ${arg.type.fullName} by fargs\n"
        }
        file += "}\n"

        return ctxClassName
    }
}