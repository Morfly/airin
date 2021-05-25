package com.morlfy.airin.starlark.lang


/**
 *
 */
typealias StringType = CharSequence

/**
 *
 */
typealias IntegerType = Comparable<Long>

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
typealias Name = StringType

/**
 *
 */
typealias Label = StringType


/**
 *
 */
sealed interface UnspecifiedValue

/**
 *
 */
object UnspecifiedString : UnspecifiedValue,
    StringType by ""

/**
 *
 */
object UnspecifiedInteger : UnspecifiedValue,
    IntegerType by 0L

/**
 *
 */
object UnspecifiedBoolean : UnspecifiedValue,
    BooleanType by false

/**
 *
 */
object UnspecifiedList : UnspecifiedValue,
    List<Nothing> by emptyList()

/**
 *
 */
object UnspecifiedDictionary : UnspecifiedValue,
    Map<Key, Value> by emptyMap()

/**
 *
 */
object UnspecifiedAnyValue : UnspecifiedValue