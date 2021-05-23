@file:Suppress("LocalVariableName")

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.feature.AssignmentsFeature
import com.morlfy.airin.starlark.lang.feature.BinaryPlusFeature
import com.morlfy.airin.starlark.lang.feature.CollectionsFeature


private fun AssignmentsFeatureUnderCompilationTest.CompilationTests() {

    // ==============================
    // ===== String assignments =====
    // ==============================

    val STRING_VARIABLE by "value"
    val STRING_VARIABLE_2 by STRING_VARIABLE
    val STRING_VARIABLE_3 by "value1" `+` "value2" `+` STRING_VARIABLE_2 `+` null
    val STRING_VARIABLE_4 by STRING_VARIABLE_2 `+` "value2"
    val STRING_VARIABLE_5 by STRING_VARIABLE_3 `+` STRING_VARIABLE_4


    // ============================
    // ===== List assignments =====
    // ============================

    val LIST_VARIABLE by list["item"]
    val LIST_VARIABLE_2 by LIST_VARIABLE
    val LIST_VARIABLE_3 by list["item1"] `+` list["item2"] `+` LIST_VARIABLE_2 `+` null `+` list()
    val LIST_VARIABLE_4 by LIST_VARIABLE_2 `+` list["item2"]
    val LIST_VARIABLE_5 by LIST_VARIABLE_3 `+` LIST_VARIABLE_4
    val LIST_VARIABLE_6 by list() `+` list[""]


    // ==================================
    // ===== Dictionary assignments =====
    // ==================================

    val DICT_VARIABLE by dict { "key" to "value" }
    val DICT_VARIABLE_2 by DICT_VARIABLE
    val DICT_VARIABLE_3 by dict { "key1" to 1 } `+` dict { "key2" to "value2" } `+` { "key3" to "value3" } `+` null
    val DICT_VARIABLE_4 by DICT_VARIABLE_2 `+` { "key2" to 2 }
    val DICT_VARIABLE_5 by DICT_VARIABLE_3 `+` DICT_VARIABLE_4
}


private interface AssignmentsFeatureUnderCompilationTest :
// Feature under test
    AssignmentsFeature,
// Additional features for compatibility tests
    BinaryPlusFeature,
    CollectionsFeature
