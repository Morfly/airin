@file:Suppress("LocalVariableName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.Expression


/**
 *
 */
class DictionaryContext : MappingFeature, DynamicBinaryPlusFeature, CollectionsFeature {

    override val kwargs = mutableMapOf<Expression?, Expression?>()
}