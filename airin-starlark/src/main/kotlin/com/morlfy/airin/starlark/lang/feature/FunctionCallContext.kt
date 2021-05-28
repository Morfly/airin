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

@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.*
import com.morlfy.airin.starlark.lang.api.LanguageFeatureScope
import kotlin.reflect.KProperty


/**
 *
 */
@LanguageFeatureScope
open class FunctionCallContext : ArgumentsFeature, BinaryPlusFeature, DynamicBinaryPlusFeature, CollectionsFeature {

    override val fargs = linkedSetOf<Argument>()


    operator fun <V> Set<Argument>.getValue(thisRef: Any?, property: KProperty<*>): V {
        error("")
    }

    operator fun <V : StringType?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs += Argument(id = property.name, value = Expression(value, ::StringLiteral))
    }

    operator fun <T, V: Comparable<T>?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs += Argument(id = property.name, value = Expression(value))
    }

    operator fun <T, V : List<T>?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs += Argument(id = property.name, value = Expression(value, ::ListExpression))
    }

    operator fun <K, V, V1 : Map<K, V>?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V1) {
        fargs += Argument(id = property.name, value = Expression(value, ::DictionaryExpression))
    }
}