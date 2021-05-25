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

package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.feature.BinaryPlusFeature
import com.morlfy.airin.starlark.lang.feature.CollectionsFeature
import com.morlfy.airin.starlark.lang.feature.DynamicBinaryPlusFeature
import com.morlfy.airin.starlark.lang.feature.MappingFeature


private fun MappingFeatureUnderCompilationTest.CompilationTests() {

    // =========================
    // ===== String values =====
    // =========================

    "key" to "value"
    "key" to "value1" `+` "value2"
    "key1" `+` "key2" to "value1"
    "key1" `+` "key2" to "value1" `+` "value2"


    // =======================
    // ===== List values =====
    // =======================

    "key" to list["item"]
    "key" to list["item1"] `+` list["item2"]
    "key" to list("item")
    "key" to list("item1") `+` list("item2")
    "key" to listOf("item")
    "key" to listOf("item1") `+` listOf("item2")
    "key" to list()
    "key" to listOf("item1") `+` list["item2"] `+` list("item3") `+` list()
    "key1" `+` "key2" to list["item"]


    // =============================
    // ===== Dictionary values =====
    // =============================

    "key" to dict {}
    "key" to dict {} `+` {}
    "key" to {}
    "key" to {} `+` dict {}
    "key1" `+` "key2" to dict {}
    "key1" `+` "key2" to {}


    // =============================
    // ===== Other values =====
    // =============================

    "key" to null
    "key" to null `+` "value"
    "key" to null `+` list["item"]
    "key" to null `+` dict {}
    "key" to null `+` {}
    "key" to null `+` Any()
    "key" to Any()
}


private interface MappingFeatureUnderCompilationTest :
    MappingFeature,

    CollectionsFeature,
    DynamicBinaryPlusFeature,
    BinaryPlusFeature