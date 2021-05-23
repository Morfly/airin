@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.lang.feature


/**
 *
 */
internal interface DynamicFunctionsFeature : LanguageFeature, StatementsHolder {

    /**
     *
     */
    operator fun String.invoke(body: FunctionCallContext.() -> Unit) {
        val args = FunctionCallContext().apply(body).args
        registerFunctionCallStatement(name = this, args)
    }

    /**
     *
     */
    operator fun String.invoke() {
        registerFunctionCallStatement(name = this)
    }
}