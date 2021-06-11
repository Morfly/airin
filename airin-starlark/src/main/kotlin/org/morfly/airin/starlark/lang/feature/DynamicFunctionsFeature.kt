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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.lang.api.LanguageFeature
import org.morfly.airin.starlark.lang.api.StatementsHolder


/**
 * Feature that enables declaring function statements using string as a name.
 *
 * Example:
 * ```
 * "java_binary" {
 *     "name" `=` "app"
 * }
 * ```
 */
internal interface DynamicFunctionsFeature : LanguageFeature, StatementsHolder {

    /**
     *
     */
    operator fun String.invoke(body: FunctionCallContext.() -> Unit) {
        val args = FunctionCallContext().apply(body).fargs
        registerFunctionCallStatement(name = this, args)
    }

    /**
     *
     */
    operator fun String.invoke() {
        registerFunctionCallStatement(name = this)
    }
}