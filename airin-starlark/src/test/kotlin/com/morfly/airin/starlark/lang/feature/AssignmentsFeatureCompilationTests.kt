@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.feature.*


private interface AssignmentsFeatureUnderCompilationTest :
// Features under test
    AssignmentsFeature,
    ReassignmentsFeature,
// Additional features for compatibility tests
    BinaryPlusFeature,
    DynamicBinaryPlusFeature,
    CollectionsFeature


private fun AssignmentsFeatureUnderCompilationTest.CompilationTests() {

    // ==============================
    // ===== String assignments =====
    // ==============================

    val STRING_VARIABLE by "value"
    val STRING_VARIABLE_2 by STRING_VARIABLE
    val STRING_VARIABLE_3 by "value1" `+` "value2" `+` STRING_VARIABLE_2 `+` null
    val STRING_VARIABLE_4 by STRING_VARIABLE_2 `+` "value2"
    val STRING_VARIABLE_5 by STRING_VARIABLE_3 `+` STRING_VARIABLE_4

    STRING_VARIABLE `=` "new_value"
    STRING_VARIABLE_2 `=` STRING_VARIABLE
    STRING_VARIABLE_3 `=` "new_value1" `+` "new_value2" `+` STRING_VARIABLE_2 `+` null
    STRING_VARIABLE_4 `=` STRING_VARIABLE_4 `+` "new_value2"
    STRING_VARIABLE_5 `=` STRING_VARIABLE_3 `+` STRING_VARIABLE_4 `+` STRING_VARIABLE_5
    STRING_VARIABLE `=` null


    // ============================
    // ===== List assignments =====
    // ============================

    val LIST_VARIABLE by list["item"]
    val LIST_VARIABLE_2 by LIST_VARIABLE
    val LIST_VARIABLE_3 by list["item1"] `+` list["item2"] `+` LIST_VARIABLE_2 `+` null `+` list()
    val LIST_VARIABLE_4 by LIST_VARIABLE_2 `+` list["item2"]
    val LIST_VARIABLE_5 by LIST_VARIABLE_3 `+` LIST_VARIABLE_4
    val LIST_VARIABLE_6 by list()

    LIST_VARIABLE `=` list["new_item"]
    LIST_VARIABLE_2 `=` LIST_VARIABLE
    LIST_VARIABLE_3 `=` list["new_item"] `+` list["new_item2"] `+` LIST_VARIABLE_2 `+` null
    LIST_VARIABLE_4 `=` LIST_VARIABLE_4 `+` list["new_item2"]
    LIST_VARIABLE_5 `=` LIST_VARIABLE_3 `+` LIST_VARIABLE_4 `+` LIST_VARIABLE_5
    LIST_VARIABLE_6 `=` LIST_VARIABLE_3 `+` LIST_VARIABLE_4 `+` list["new_item3"]
    LIST_VARIABLE `=` null


    // ==================================
    // ===== Dictionary assignments =====
    // ==================================

    val DICT_VARIABLE by dict { "key" to "value" }
    val DICT_VARIABLE_2 by DICT_VARIABLE
    val DICT_VARIABLE_3 by dict { "key1" to 1 } `+` dict { "key2" to "value2" } `+` { "key3" to "value3" } `+` null
    val DICT_VARIABLE_4 by DICT_VARIABLE_2 `+` { "key2" to 2 }
    val DICT_VARIABLE_5 by DICT_VARIABLE_3 `+` DICT_VARIABLE_4

    DICT_VARIABLE `=` dict { "new_key" to "new_value" }
    DICT_VARIABLE `=` { "new_key" to "new_value" }
    DICT_VARIABLE_3 `=` { 1 to 1 } `+` dict { 2 to 2 } `+` DICT_VARIABLE_2 `+` null
    DICT_VARIABLE_4 `=` DICT_VARIABLE_4 `+` { 2 to "value2" }
    DICT_VARIABLE_5 `=` DICT_VARIABLE_3 `+` DICT_VARIABLE_4 `+` DICT_VARIABLE_5
    DICT_VARIABLE `=` null
}