package com.morfly.airin.starlark.elements

import com.morlfy.airin.starlark.elements.DictionaryReference
import com.morlfy.airin.starlark.elements.ListReference
import com.morlfy.airin.starlark.elements.StringReference
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


private fun ReferencesCompillationTests() {
    val stringRef: StringType = StringReference(name = "variable")

    val listRef: List<StringType> = ListReference(name = "variable")

    val dictRef: Map<Key, Value> = DictionaryReference("variable")
}