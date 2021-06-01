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

@file:Suppress("LocalVariableName")

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.DictionaryReference
import org.morfly.airin.starlark.elements.ListReference
import org.morfly.airin.starlark.elements.StringReference
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value


private fun DynamicArgumentsFeatureUnderCompilationTest.CompilationTests() {

    // =======================
    // ===== String args =====
    // =======================

//    val STRING_VAR by "var_value"
    val STRING_VAR = StringReference("STRING_VAR")
    "string_arg" `=` STRING_VAR
    "string_arg" `=` "value"
    "string_arg" `=` "value" `+` STRING_VAR


    // =====================
    // ===== List args =====
    // =====================

    val LIST_VAR = ListReference<StringType>("LIST_VAR")
    "list_arg" `=` LIST_VAR
    "list_arg" `=` listOf("item")
    "list_arg" `=` list["item"]
    "list_arg" `=` list("item")
    "list_arg" `=` list<StringType>()
    "list_arg" `=` list<StringType>() `+` list["item"]
    "list_arg" `=` list() // `+` list["item"]
    "list_arg" `=` list["item"] `+` list() `+` list["item"]
//    "list_arg" `=` list["item"] `+` LIST_VAR TODO
    "list_arg" `=` LIST_VAR `+` list["item"]


    // ===========================
    // ===== Dictionary args =====
    // ===========================

//    val DICT_VAR by dict { "key" to "value" }
    val DICT_VAR = DictionaryReference<Key, Value>("DICT_VAR")
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


private interface DynamicArgumentsFeatureUnderCompilationTest :
// Features under test
    DynamicArgumentsFeature,
// Additional features for compatibility tests
    CollectionsFeature,
    DynamicBinaryPlusFeature
