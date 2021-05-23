package com.morlfy.airin.starlark.lang

import com.morlfy.airin.starlark.elements.Statement
import com.morlfy.airin.starlark.lang.feature.*


@LanguageFeatureContext
sealed class StarlarkContext : StatementsHolder,
    AssignmentsFeature, BinaryPlusFeature, DynamicBinaryPlusFeature, CollectionsFeature,
    DynamicFunctionsFeature, EmptyLinesFeature, RawTextFeature {

    override val statements = mutableListOf<Statement>()
}
