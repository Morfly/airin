package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.*
import org.morfly.airin.starlark.lang.BooleanType
import org.morfly.airin.starlark.lang.NumberType
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.TupleType
import org.morfly.airin.starlark.lang.api.ArgumentsHolder
import org.morfly.airin.starlark.lang.api.append
import kotlin.reflect.KProperty

internal interface ArgumentsFeature : ArgumentsHolder {

    operator fun <V> Map<String, Argument>.getValue(thisRef: Any?, property: KProperty<*>): V {
        error("Unable to return value from a function argument.")
    }

    operator fun <V : StringType?> Map<String, Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs[property.name] = Argument(id = property.name, value = Expression(value, ::StringLiteral))
    }

    operator fun <V : NumberType?> Map<String, Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs[property.name] = Argument(id = property.name, value = Expression(value, ::NumberLiteral))
    }

    operator fun <V : BooleanType?> Map<String, Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs[property.name] = Argument(id = property.name, value = Expression(value, ::BooleanLiteral))
    }

    operator fun <T, V : List<T>?> Map<String, Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        append(
            name = property.name,
            value = Expression(value, ::ListExpression),
            concatenation = { left, op, right -> ListBinaryOperation<T>(left, op, right) }
        )
    }

    operator fun <V : TupleType?> Map<String, Argument>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        fargs[property.name] = Argument(id = property.name, value = Expression(value, ::TupleExpression))
    }

    operator fun <K, V, V1 : Map<K, V>?> Map<String, Argument>.setValue(
        thisRef: Any?, property: KProperty<*>, value: V1
    ) {
        append(
            name = property.name,
            value = Expression(value, ::DictionaryExpression),
            concatenation = { left, op, right -> DictionaryBinaryOperation<K, V>(left, op, right) }
        )
    }
}