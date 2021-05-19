package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.feature.BinaryPlusFeature


private fun BinaryPlusFeature.CompilationTest() {
    "string1" `+` "string2"
    "string1" `+` null
    null `+` "string2"

    val list1 = listOf(1, 2, 3)
    val list2 = listOf(4, 5, 6)
    list1 `+` list2
    list1 `+` null
    null `+` list2

    val dictionary1 = mapOf("key1" to "value1")
    val dictionary2 = mapOf(2 to 2)
    dictionary1 `+` dictionary2
    dictionary1 `+` null
    null `+` dictionary2

    dictionary1 `+` {}
    null `+` {}
}