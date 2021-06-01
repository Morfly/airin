package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.Key
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.Value
import org.morfly.airin.starlark.lang.api.LanguageFeature
import kotlin.reflect.KProperty


/**
 * Feature that enables assignments for variables with names that can vary based on template arguments.
 */
internal interface DynamicAssignmentsFeature : LanguageFeature, StarlarkStatementsHolder {

    operator fun _StringValueAccumulator.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): StringReference {
        val assignment = this.holder as Assignment
        statements += assignment
        return StringReference(name = assignment.name)
    }

    operator fun <T> _ListValueAccumulator<T>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): ListReference<T> {
        val assignment = this.holder as Assignment
        statements += assignment
        return ListReference(name = assignment.name)
    }

    operator fun <K : Key, V : Value> _DictionaryValueAccumulator<K, V>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): DictionaryReference<K, V> {
        val assignment = this.holder as Assignment
        statements += assignment
        return DictionaryReference(name = assignment.name)
    }


    infix fun String.`=`(value: StringType): _StringValueAccumulator {
        val assignment = Assignment(name = this, value = Expression(value, ::StringLiteral))
        return _StringValueAccumulator(assignment)
    }

    infix fun <T> String.`=`(value: List<T>): _ListValueAccumulator<T> {
        val assignment = Assignment(name = this, value = Expression(value, ::ListExpression))
        return _ListValueAccumulator(assignment)
    }

    infix fun <K : Key, V : Value> String.`=`(value: Map<K, V>): _DictionaryValueAccumulator<K, V> {
        val assignment = Assignment(name = this, value = Expression(value, ::DictionaryExpression))
        return _DictionaryValueAccumulator(assignment)
    }

    infix fun <K : Key, V : Value> String.`=`(body: DictionaryContext.() -> Unit): _DictionaryValueAccumulator<K, V> {
        val value = DictionaryContext().apply(body).kwargs
        val assignment = Assignment(name = this, value = Expression(value, ::DictionaryExpression))
        return _DictionaryValueAccumulator(assignment)
    }
}