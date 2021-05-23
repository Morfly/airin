package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value
import kotlin.reflect.KProperty


/**
 *
 */
internal interface AssignmentsFeature : LanguageFeature, StatementsHolder {

    /**
     *
     */
    operator fun StringType.provideDelegate(thisRef: AssignmentsFeature?, property: KProperty<*>): StringReference {
        statements += Assignment(name = property.name, value = Expression(this))
        return StringReference(name = property.name)
    }

    /**
     *
     */
    operator fun StringReference.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): StringReference = this

    /**
     *
     */
    operator fun <T> List<T>.provideDelegate(thisRef: AssignmentsFeature?, property: KProperty<*>): ListReference<T> {
        statements += Assignment(name = property.name, value = Expression(this))
        return ListReference(name = property.name)
    }

    /**
     *
     */
    operator fun <T> ListReference<T>.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): ListReference<T> = this

    /**
     *
     */
    operator fun <K : Key, V : Value> Map<K, V>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): DictionaryReference<K, V> {
        statements += Assignment(name = property.name, value = Expression(this))
        return DictionaryReference(name = property.name)
    }

    /**
     *
     */
    operator fun <K : Key, V : Value> DictionaryReference<K, V>.getValue(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): DictionaryReference<K, V> = this
}