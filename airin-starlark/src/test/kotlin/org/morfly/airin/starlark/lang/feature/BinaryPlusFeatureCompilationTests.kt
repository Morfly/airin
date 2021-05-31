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

@file:Suppress("JoinDeclarationAndAssignment", "LocalVariableName")

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value


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