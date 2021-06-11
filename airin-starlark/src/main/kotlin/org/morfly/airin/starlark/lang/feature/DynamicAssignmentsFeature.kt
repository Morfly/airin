package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.*
import org.morfly.airin.starlark.lang.api.LanguageFeature
import org.morfly.airin.starlark.lang.api.StatementsHolder
import kotlin.reflect.KProperty


/**
 * Feature that enables assignments for variables with names that can vary based on template arguments.
 */
internal interface DynamicAssignmentsFeature : LanguageFeature, StatementsHolder {

    operator fun _StringValueAccumulator.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): StringReference {
        val assignment = this.holder as Assignment
        statements += assignment
        return StringReference(name = assignment.name)
    }

    operator fun _NumberValueAccumulator.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): NumberReference {
        val assignment = this.holder as Assignment
        statements += assignment
        return NumberReference(name = assignment.name)
    }

    operator fun _BooleanValueAccumulator.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): BooleanReference {
        val assignment = this.holder as Assignment
        statements += assignment
        return BooleanReference(name = assignment.name)
    }

    operator fun <T> _ListValueAccumulator<T>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): ListReference<T> {
        val assignment = this.holder as Assignment
        statements += assignment
        return ListReference(name = assignment.name)
    }

    operator fun _TupleValueAccumulator.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): TupleReference {
        val assignment = this.holder as Assignment
        statements += assignment
        return TupleReference(name = assignment.name)
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

    infix fun String.`=`(value: NumberType): _NumberValueAccumulator {
        val assignment = Assignment(name = this, value = Expression(value, ::NumberLiteral))
        return _NumberValueAccumulator(assignment)
    }

    infix fun String.`=`(value: BooleanType): _BooleanValueAccumulator {
        val assignment = Assignment(name = this, value = Expression(value, ::BooleanLiteral))
        return _BooleanValueAccumulator(assignment)
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