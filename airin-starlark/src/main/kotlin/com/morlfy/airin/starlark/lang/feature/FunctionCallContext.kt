@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.Argument


/**
 *
 */
open class FunctionCallContext : ArgumentsFeature, BinaryPlusFeature, DynamicBinaryPlusFeature, CollectionsFeature {

    override val args = linkedSetOf<Argument>()
}