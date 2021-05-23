@file:Suppress("JoinDeclarationAndAssignment", "LocalVariableName")

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value
import com.morlfy.airin.starlark.lang.feature.AssignmentsFeature
import com.morlfy.airin.starlark.lang.feature.BinaryPlusFeature
import com.morlfy.airin.starlark.lang.feature.CollectionsFeature


private fun BinaryPlusFeatureUnderCompilationTest.CompilationTests() {

    // ================================
    // ===== String concatenation =====
    // ================================

    var stringType: StringType

    val STRING_VARIABLE by "value"

    stringType = "value" `+` "value"
    stringType = "value" `+` null
    stringType = null `+` "value"
    stringType = "value" `+` STRING_VARIABLE
    stringType = STRING_VARIABLE `+` "value"


    // ==============================
    // ===== List concatenation =====
    // ==============================

    var listType: List<StringType>

    val LIST_VARIABLE by list["item"]

    listType = list["item"] `+` list["item"]
    listType = list["item"] `+` null
    listType = null `+` list["item"]
    listType = list["item"] `+` LIST_VARIABLE
    listType = LIST_VARIABLE `+` list["item"]
    listType = listOf("item") `+` list["item"]
    listType = list["item"] `+` listOf("item")
    listType = list() `+` list["item"]
    listType = list["item"] `+` list()
    listType = list() `+` null
    listType = null `+` list()


    // ====================================
    // ===== Dictionary concatenation =====
    // ====================================

    var dictType: Map<Key, Value>

    val DICT_VARIABLE by dict { "key" to "value" }

    dictType = dict {} `+` dict {}
    dictType = dict {} `+` {}
    dictType = dict {} `+` null
    dictType = null `+` dict {}
    dictType = null `+` {}
    dictType = dict {} `+` DICT_VARIABLE
    dictType = DICT_VARIABLE `+` {}
    dictType = dict {} `+` dict("key" to "value")
    dictType = dict("key" to "value") `+` {}
}


private interface BinaryPlusFeatureUnderCompilationTest :
// Feature under test
    BinaryPlusFeature,
// Additional features for compatibility tests
    AssignmentsFeature,
    CollectionsFeature