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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.BooleanType
import org.morfly.airin.starlark.lang.NumberType
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.TupleType
import org.morfly.airin.starlark.lang.api.LanguageFeatureScope
import kotlin.reflect.KProperty


/**
 *
 */
@LanguageFeatureScope
open class FunctionCallContext :
    DynamicArgumentsFeature, BinaryPlusFeature, DynamicBinaryPlusFeature,
    CollectionsFeature, BooleanValuesFeature,
    StringExtensionsFeature {

    override val fargs = linkedSetOf<Argument>()


    operator fun <V> Set<Argument>.getValue(thisRef: Any?, property: KProperty<*>): V {
        error("Unable to return value from a function argument.")
    }


    operator fun <V : StringType?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs += Argument(id = property.name, value = Expression(value, ::StringLiteral))
    }

    operator fun <V : NumberType?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs += Argument(id = property.name, value = Expression(value, ::NumberLiteral))
    }

    operator fun <V : BooleanType?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs += Argument(id = property.name, value = Expression(value, ::BooleanLiteral))
    }

    operator fun <T, V : List<T>?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs += Argument(id = property.name, value = Expression(value, ::ListExpression))
    }

    operator fun <V : TupleType?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs += Argument(id = property.name, value = Expression(value, ::TupleExpression))
    }

    operator fun <K, V, V1 : Map<K, V>?> Set<Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V1) {
        fargs += Argument(id = property.name, value = Expression(value, ::DictionaryExpression))
    }
}