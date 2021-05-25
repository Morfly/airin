package com.morfly.airin.starlark.lang.feature

import com.morlfy.airin.starlark.lang.feature.LoadStatementsFeature


private fun LoadStatementsFeatureUnderCompilationTest.CompilationTests() {
    load("file_name", "function_1", "function_2")
}

private interface LoadStatementsFeatureUnderCompilationTest :
// Feature under test
    LoadStatementsFeature