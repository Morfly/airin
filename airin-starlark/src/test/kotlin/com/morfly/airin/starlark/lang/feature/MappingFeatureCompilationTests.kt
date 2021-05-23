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