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

//    /**
//     * TODO not sure if needed
//     */
//    operator fun Any?.getValue(thisRef: AssignmentsFeature?, property: KProperty<*>): AnyReference {
//        statements += Assignment(name = property.name, value = Expression(this))
//        return AnyReference(name = property.name)
//    }
//    operator fun Map<Key, Value>.getValue(
//        thisRef: AssignmentsFeature?, property: KProperty<*>
//    ): DictionaryReference<Key, Value> {
//        statements += Assignment(name = property.name, value = DictionaryExpression(this))
//        return DictionaryReference(name = property.name)
//    }
}


/**
 *
 */
internal interface ReassignmentsFeature : LanguageFeature, StatementsHolder {

    /**
     *
     */
    infix fun StringReference.`=`(value: StringType?): ValueAccumulator<StringType> {
        val assignment = Assignment(name, value = value?.let(::StringLiteral))
        statements += assignment
        return ValueAccumulator(assignment)
    }

    /**
     *
     */
    infix fun <T> ListReference<T>.`=`(value: List<T>?): ValueAccumulator<List<T>> {
        val assignment = Assignment(name, value = value?.let(::ListExpression))
        statements += assignment
        return ValueAccumulator(assignment)
    }

    /**
     *
     */
    infix fun <K : Key, V : Value> DictionaryReference<K, V>.`=`(value: Map<Key, Value>?): ValueAccumulator<Map<K, V>> {
        val assignment = Assignment(name, value = value?.let(::DictionaryExpression))
        statements += assignment
        return ValueAccumulator(assignment)
    }

    /**
     *
     */
    infix fun <K : Key, V : Value> DictionaryReference<K, V>.`=`(body: DictionaryContext.() -> Unit): ValueAccumulator<Map<K, V>> {
        val value = DictionaryContext().apply(body).dictionary
        val assignment = Assignment(name, value = DictionaryExpression(value))
        statements += assignment
        return ValueAccumulator(assignment)
    }

//    /**
//     * TODO not sure if needed
//     */
//    infix fun AnyReference.`=`(value: Any?): ValueAccumulator<Any> {
//        val assignment = Assignment(name, value = Expression(value))
//        statements += assignment
//        return ValueAccumulator(assignment)
//    }
}