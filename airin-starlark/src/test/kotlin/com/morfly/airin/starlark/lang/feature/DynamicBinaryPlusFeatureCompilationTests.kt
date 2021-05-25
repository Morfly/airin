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

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.DictionaryExpression
import com.morlfy.airin.starlark.elements.DynamicValue
import com.morlfy.airin.starlark.elements.ListExpression
import com.morlfy.airin.starlark.elements.StringLiteral
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value
import com.morlfy.airin.starlark.lang.feature.AssignmentsFeature
import com.morlfy.airin.starlark.lang.feature.CollectionsFeature
import com.morlfy.airin.starlark.lang.feature.DynamicBinaryPlusFeature
import com.morlfy.airin.starlark.lang.feature._ValueAccumulator


private fun DynamicBinaryPlusFeatureUnderCompilationTest.CompilationTests() {

    // ================================
    // ===== String concatenation =====
    // ================================

    val stringValue = _ValueAccumulator<StringType>(DynamicValue(StringLiteral("value")))

    val STRING_VARIABLE by "value"

    stringValue `+` "value"
    stringValue `+` null
    stringValue `+` null `+` "value"
    stringValue `+` STRING_VARIABLE
    stringValue `+` STRING_VARIABLE `+` "value"


    // ==============================
    // ===== List concatenation =====
    // ==============================

    val listValue = _ValueAccumulator<List<StringType>>(DynamicValue(ListExpression(listOf("item"))))

    val LIST_VARIABLE by list["item"]

    listValue `+` list["item"]
    listValue `+` list("item")
    listValue `+` listOf("item")
    listValue `+` list()
    listValue `+` null
    listValue `+` null `+` list["item"]
    listValue `+` null `+` list("item")
    listValue `+` null `+` listOf("item")
    listValue `+` null `+` list()
    listValue `+` LIST_VARIABLE
    listValue `+` LIST_VARIABLE `+` list["item"]
    listValue `+` LIST_VARIABLE `+` list("item")
    listValue `+` LIST_VARIABLE `+` listOf("item")
    listValue `+` LIST_VARIABLE `+` list()


    // ====================================
    // ===== Dictionary concatenation =====
    // ====================================

    val dictValue = _ValueAccumulator<Map<Key, Value>>(DynamicValue(DictionaryExpression(mapOf("key" to "value"))))

    val DICT_VARIABLE by dict {}

    dictValue `+` dict { "key" to "value" }
    dictValue `+` { "key" to "value" }
    dictValue `+` dict("key" to "value")
    dictValue `+` mapOf("key" to "value")
    dictValue `+` dict {}
    dictValue `+` {}
    dictValue `+` null
    dictValue `+` null `+` dict { "key" to "value" }
    dictValue `+` null `+` { "key" to "value" }
    dictValue `+` null `+` dict("key" to "value")
    dictValue `+` null `+` mapOf("key" to "value")
    dictValue `+` null `+` dict {}
    dictValue `+` null `+` {}
    dictValue `+` DICT_VARIABLE
    dictValue `+` DICT_VARIABLE `+` dict { "key" to "value" }
    dictValue `+` DICT_VARIABLE `+` { "key" to "value" }
    dictValue `+` DICT_VARIABLE `+` dict("key" to "value")
    dictValue `+` DICT_VARIABLE `+` mapOf("key" to "value")
    dictValue `+` DICT_VARIABLE `+` dict {}
    dictValue `+` DICT_VARIABLE `+` {}


    // ====================================
    // ===== Other concatenations =========
    // ====================================

    val value = _ValueAccumulator<Any>(DynamicValue(null))

    value `+` Any()
    value `+` null
}


private interface DynamicBinaryPlusFeatureUnderCompilationTest :
// Feature under test
    DynamicBinaryPlusFeature,
// Additional features for compatibility tests
    AssignmentsFeature,
    CollectionsFeature