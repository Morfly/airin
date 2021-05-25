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

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.RawStatement
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StarlarkContext
import com.morlfy.airin.starlark.lang.Value


internal interface RawTextFeature : LanguageFeature, StatementsHolder {

    val String.txt: Unit
        get() {
            statements += RawStatement(value = this)
        }


}

inline fun <reified T : Any> String.txt(): T {
    return TODO()
}

//fun String.txt(): AnyReference {
//
//}

//infix fun String.`=`(any: Any?) {
//
//}

//infix fun Any?.`+`(other: Any?): String = ""

fun StarlarkContext.test() {
    """
            NAME = "some name"
            VIEW_MODELS = ["vm1", "vm2"]
        """.trimIndent().txt

    "NAME".txt

    val list: List<String> = "NAME".txt()
    val str: CharSequence = "" + "NAME".txt() `+` ""
    val dict: Map<Key, Value> = "NAME".txt()
    val any: Any = "NAME".txt()

//    "arg" `=` "NAME".txt() `+` ""
}