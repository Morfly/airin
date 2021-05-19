package com.morfly.airin.starlark.elements

import com.morlfy.airin.starlark.elements.AnyBinaryOperation
import com.morlfy.airin.starlark.elements.BinaryOperator.PLUS
import com.morlfy.airin.starlark.elements.DictionaryBinaryOperation
import com.morlfy.airin.starlark.elements.ListBinaryOperation
import com.morlfy.airin.starlark.elements.StringBinaryOperation
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value
import io.kotest.core.spec.style.ShouldSpec


class BinaryOperationsTests : ShouldSpec({

    should("'BinaryOperation' types take nullable 'left' and 'right' 'Expression' properties") {
        StringBinaryOperation(null, PLUS, null)
        ListBinaryOperation<String>(null, PLUS, null)
        DictionaryBinaryOperation<Key, Value>(null, PLUS, null)
        AnyBinaryOperation(null, PLUS, null)
    }

    should("'StringBinaryOperation' be compatible with string type") {
        val string: StringType = StringBinaryOperation(null, PLUS, null)
    }

    should("'ListBinaryOperation' be compatible with list type") {
        val list: List<String> = ListBinaryOperation(null, PLUS, null)
    }

    should("'DictionaryBinaryOperation' be compatible with dictionary type") {
        val map: Map<Key, Value> = DictionaryBinaryOperation(null, PLUS, null)
    }
})