package com.morlfy.airin.starlark.lang


/**
 *
 */
typealias StringType = CharSequence

/**
 *
 */
typealias IntegerType = Comparable<Int>

/**
 *
 */
typealias FloatType = Comparable<Double>

/**
 *
 */
typealias BooleanType = Comparable<Boolean>

/**
 *
 */
typealias Key = Any?

/**
 *
 */
typealias Value = Any?

/**
 *
 */
//data class KeyValuePair(val key: Key, var value: Value)

/**
 *
 */
typealias Name = StringType

/**
 *
 */
typealias Label = StringType


typealias NotSpecified = Any // TODO implement as expression