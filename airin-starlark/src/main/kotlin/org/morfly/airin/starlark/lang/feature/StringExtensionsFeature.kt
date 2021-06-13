package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.Expression
import org.morfly.airin.starlark.elements.StringFunctionCall
import org.morfly.airin.starlark.elements.StringLiteral
import org.morfly.airin.starlark.lang.StringType


interface StringExtensionsFeature {

    fun StringType.format(body: FunctionCallContext.() -> Unit): StringType =
        StringFunctionCall(
            name = "format",
            args = FunctionCallContext().apply(body).fargs,
            receiver = Expression(this, ::StringLiteral)
        )
}