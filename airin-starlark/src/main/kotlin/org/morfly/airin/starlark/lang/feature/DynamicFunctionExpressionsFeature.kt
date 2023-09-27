package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.lang.api.LanguageFeature

internal interface DynamicFunctionExpressionsFeature : LanguageFeature

context(DynamicFunctionExpressionsFeature)
inline operator fun <reified T> String.invoke(body: FunctionCallContext.() -> Unit): T {
    return functionCallExpression<T, FunctionCallContext>(name = this, FunctionCallContext(), body)
}

context(DynamicFunctionExpressionsFeature)
inline operator fun <reified T> String.invoke(): T {
    return functionCallExpression(name = this, emptySet())
}
