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