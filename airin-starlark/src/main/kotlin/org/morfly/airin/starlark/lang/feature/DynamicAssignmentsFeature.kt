@file:Suppress("FunctionName", "unused")

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

    operator fun _StringExpressionAccumulator<Assignment>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): StringReference {
        val assignment = this.holder.host
        statements += assignment
        return StringReference(name = assignment.name)
    }

    operator fun _NumberExpressionAccumulator<Assignment>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): NumberReference {
        val assignment = this.holder.host
        statements += assignment
        return NumberReference(name = assignment.name)
    }

    operator fun _BooleanExpressionAccumulator<Assignment>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): BooleanReference {
        val assignment = this.holder.host
        statements += assignment
        return BooleanReference(name = assignment.name)
    }

    operator fun <T> _ListExpressionAccumulator<T, Assignment>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): ListReference<T> {
        val assignment = this.holder.host
        statements += assignment
        return ListReference(name = assignment.name)
    }

    operator fun _TupleExpressionAccumulator<Assignment>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): TupleReference {
        val assignment = this.holder.host
        statements += assignment
        return TupleReference(name = assignment.name)
    }

    operator fun <K : Key, V : Value> _DictionaryExpressionAccumulator<K, V, Assignment>.provideDelegate(
        thisRef: AssignmentsFeature?, property: KProperty<*>
    ): DictionaryReference<K, V> {
        val assignment = this.holder.host
        statements += assignment
        return DictionaryReference(name = assignment.name)
    }


    infix fun String.`=`(value: StringType): _StringExpressionAccumulator<Assignment> {
        val assignment = Assignment(name = this, value = Expression(value, ::StringLiteral))
        return _StringExpressionAccumulator(assignment)
    }

    infix fun String.`=`(value: NumberType): _NumberExpressionAccumulator<Assignment> {
        val assignment = Assignment(name = this, value = Expression(value, ::NumberLiteral))
        return _NumberExpressionAccumulator(assignment)
    }

    infix fun String.`=`(value: BooleanType): _BooleanExpressionAccumulator<Assignment> {
        val assignment = Assignment(name = this, value = Expression(value, ::BooleanLiteral))
        return _BooleanExpressionAccumulator(assignment)
    }

    infix fun <T> String.`=`(value: List<T>): _ListExpressionAccumulator<T, Assignment> {
        val assignment = Assignment(name = this, value = Expression(value, ::ListExpression))
        return _ListExpressionAccumulator(assignment)
    }

    infix fun <K : Key, V : Value> String.`=`(value: Map<K, V>): _DictionaryExpressionAccumulator<K, V, Assignment> {
        val assignment = Assignment(name = this, value = Expression(value, ::DictionaryExpression))
        return _DictionaryExpressionAccumulator(assignment)
    }

    infix fun <K : Key, V : Value> String.`=`(
        body: DictionaryContext.() -> Unit
    ): _DictionaryExpressionAccumulator<K, V, Assignment> {
        val value = DictionaryContext().apply(body).kwargs
        val assignment = Assignment(name = this, value = Expression(value, ::DictionaryExpression))
        return _DictionaryExpressionAccumulator(assignment)
    }
}