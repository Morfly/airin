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

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature
import org.morfly.airin.starlark.lang.api.StatementsHolder
import kotlin.reflect.KProperty


/**
 * Feature that enables Starlark variable assignment statements.
 */
internal interface AssignmentsFeature : LanguageFeature, StatementsHolder {

    operator fun StringType.provideDelegate(thisRef: AssignmentsFeature?, property: KProperty<*>): StringReference {
        statements += Assignment(name = property.name, value = Expression(this, ::StringLiteral))
        return StringReference(name = property.name)
    }

    operator fun StringReference.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): StringReference = this


    operator fun NumberType.provideDelegate(thisRef: AssignmentsFeature?, property: KProperty<*>): NumberReference {
        statements += Assignment(name = property.name, value = Expression(this, ::NumberLiteral))
        return NumberReference(name = property.name)
    }

    operator fun NumberReference.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): NumberReference = this


    operator fun BooleanType.provideDelegate(thisRef: AssignmentsFeature?, property: KProperty<*>): BooleanReference {
        statements += Assignment(name = property.name, value = Expression(this, ::BooleanLiteral))
        return BooleanReference(name = property.name)
    }

    operator fun BooleanReference.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): BooleanReference = this


    operator fun <T> List<T>.provideDelegate(thisRef: AssignmentsFeature?, property: KProperty<*>): ListReference<T> {
        statements += Assignment(name = property.name, value = Expression(this, ::ListExpression))
        return ListReference(name = property.name)
    }

    operator fun <T> ListReference<T>.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): ListReference<T> = this


    operator fun TupleType.provideDelegate(thisRef: AssignmentsFeature?, property: KProperty<*>): TupleReference {
        statements += Assignment(name = property.name, value = Expression(this, ::TupleExpression))
        return TupleReference(name = property.name)
    }

    operator fun TupleReference.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): TupleReference = this


    operator fun <K : Key, V : Value> Map<K, V>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): DictionaryReference<K, V> {
        statements += Assignment(name = property.name, value = Expression(this, ::DictionaryExpression))
        return DictionaryReference(name = property.name)
    }

    operator fun <K : Key, V : Value> DictionaryReference<K, V>.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): DictionaryReference<K, V> = this
}