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

import com.google.devtools.ksp.symbol.KSFile
import org.morfly.airin.starlark.lang.api.BracketsKind
import org.morfly.airin.starlark.lang.api.FunctionKind
import org.morfly.airin.starlark.lang.api.FunctionScope


interface NameHolder {
    val shortName: String
    val fullName: String
    val qualifiedName: String?
}

sealed interface Descriptor : NameHolder

data class GeneratedFile(
    override val shortName: String,
    val packageName: String,
    val functions: MutableList<GeneratedFunction> = mutableListOf(),
    val originalFile: KSFile
) : NameHolder {
    override val fullName = "$shortName.kt"
    override val qualifiedName: String? = null
}

data class GeneratedFunction(
    override val shortName: String,
    val annotatedClassName: String,
    val arguments: List<Arg>,
    val vararg: Vararg?,
    val returnType: TypeDescriptor,
    val scope: Set<FunctionScope>,
    val kind: FunctionKind,
    val brackets: Set<BracketsKind>
) : Descriptor {
    override val fullName: String = shortName
    override val qualifiedName: String? = null

    val hasArgs: Boolean get() = arguments.isNotEmpty() || vararg != null
}

interface ArgumentDescriptor : Descriptor {
    val type: SpecifiedType
    val underlyingName: String
}

data class Arg(
    override val shortName: String,
    override val underlyingName: String,
    override val type: SpecifiedType,
    val isRequired: Boolean,
) : ArgumentDescriptor {
    override val fullName = shortName
    override val qualifiedName: String? = null
}

data class Vararg(
    override val shortName: String,
    override val underlyingName: String,
    override val type: SpecifiedType,
    val fullType: SpecifiedType,
    val isRequired: Boolean,
) : ArgumentDescriptor {
    override val fullName = shortName
    override val qualifiedName: String? = null
}

fun Vararg.toArgument(): Arg =
    Arg(shortName, underlyingName = "", fullType, isRequired)

sealed interface TypeDescriptor : Descriptor

data class SpecifiedType(
    override val shortName: String,
    override val qualifiedName: String,
    val packageName: String,
    val isMarkedNullable: Boolean,
    private val actual: SpecifiedType?,
    val genericArguments: List<SpecifiedType>
) : TypeDescriptor {
    private val nullabilitySuffix = if (isMarkedNullable) "?" else ""

    override val fullName: String = if (genericArguments.isEmpty()) {
        shortName + nullabilitySuffix
    } else shortName + genericArguments.joinToString(prefix = "<", separator = ", ", postfix = ">") {
        it.fullName
    } + nullabilitySuffix

    val actualType = actual ?: this
}

object DynamicType : TypeDescriptor {
    override val shortName = "<dynamic>"
    override val fullName = shortName
    override val qualifiedName: String? = null
}

val VoidType = SpecifiedType(
    shortName = Unit::class.simpleName!!,
    qualifiedName = Unit::class.qualifiedName!!,
    packageName = "kotlin",
    isMarkedNullable = false,
    actual = null,
    genericArguments = emptyList()
)