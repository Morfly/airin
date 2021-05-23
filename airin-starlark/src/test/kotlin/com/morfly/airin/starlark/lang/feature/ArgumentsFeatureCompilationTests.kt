@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.feature.ArgumentsFeature
import com.morlfy.airin.starlark.lang.feature.AssignmentsFeature
import com.morlfy.airin.starlark.lang.feature.CollectionsFeature
import com.morlfy.airin.starlark.lang.feature.DynamicBinaryPlusFeature


private fun ArgumentsFeatureUnderCompilationTest.CompilationTests() {

    // =======================
    // ===== String args =====
    // =======================

    val STRING_VAR by "var_value"
    "string_arg" `=` STRING_VAR
    "string_arg" `=` "value"
    "string_arg" `=` "value" `+` STRING_VAR


    // =====================
    // ===== List args =====
    // =====================

    val LIST_VAR by list["item"]
    "list_arg" `=` LIST_VAR
    "list_arg" `=` listOf("item")
    "list_arg" `=` list["item"]
    "list_arg" `=` list("item")
    "list_arg" `=` list<StringType>()
    "list_arg" `=` list<StringType>() `+` list["item"]
    "list_arg" `=` list() // `+` list["item"]
    "list_arg" `=` list["item"] `+` list() `+` list["item"]
    "list_arg" `=` list["item"] `+` LIST_VAR


    // ===========================
    // ===== Dictionary args =====
    // ===========================

    val DICT_VAR by dict { "key" to "value" }
    "dict_arg" `=` DICT_VAR
    "dict_arg" `=` mapOf("key" to "value")
    "dict_arg" `=` dict { "key" to "value" }
    "dict_arg" `=` dict {}
    "dict_arg" `=` { "key" to "value" }
    "dict_arg" `=` {}
    "dict_arg" `=` dict {} `+` dict {}
    "dict_arg" `=` {} `+` dict {}


    // ===========================
    // ===== Other args =====
    // ===========================

    "arg" `=` null
    "arg" `=` null `+` "value"
    "arg" `=` Any()
}


private interface ArgumentsFeatureUnderCompilationTest :
// Features under test
    ArgumentsFeature,
// Additional features for compatibility tests
    AssignmentsFeature,
    CollectionsFeature,
    DynamicBinaryPlusFeature
