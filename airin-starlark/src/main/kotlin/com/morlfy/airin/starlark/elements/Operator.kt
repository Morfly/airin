package com.morlfy.airin.starlark.elements


/**
 *
 */
sealed interface Operator {
    val value: String
}

/**
 *
 */
enum class BinaryOperator(override val value: String) : Operator {
    PLUS("+"),
    PERCENT("%");

    override fun toString() = value
}