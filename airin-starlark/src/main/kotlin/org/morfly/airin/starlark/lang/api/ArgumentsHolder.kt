package org.morfly.airin.starlark.lang.api

import org.morfly.airin.starlark.elements.Argument
import org.morfly.airin.starlark.elements.BinaryOperation
import org.morfly.airin.starlark.elements.BinaryOperator
import org.morfly.airin.starlark.elements.Expression

/**
 * Defines an entity that collects argument elements.
 */
internal interface ArgumentsHolder {

    /**
     *
     */
    val fargs: LinkedHashMap<String, Argument>
}

fun LinkedHashMap<String, Argument>.asSet() =
    mapTo(linkedSetOf()) { it.value }

internal fun ArgumentsHolder.append(
    name: String,
    value: Expression,
    concatenation: (Expression, BinaryOperator, Expression) -> BinaryOperation
): Argument {
    val argument = if (name !in fargs) {
        Argument(id = name, value = value)
    } else {
        val leftExpression = fargs[name]!!.value
        Argument(
            id = name,
            value = concatenation(leftExpression, BinaryOperator.PLUS, value)
        )
    }
    fargs[name] = argument
    return argument
}