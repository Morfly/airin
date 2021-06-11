@file:Suppress("FunctionName")

package org.morfly.airin.starlark.lang


abstract class NumberTypeDelegate internal constructor(
    private val value: NumberType = 0
) : NumberType() {
    override fun toByte() = value.toByte()
    override fun toChar() = value.toChar()
    override fun toDouble() = value.toDouble()
    override fun toFloat() = value.toFloat()
    override fun toInt() = value.toInt()
    override fun toLong() = value.toLong()
    override fun toShort() = value.toShort()
}

internal fun <T> ListTypeDelegate(value: List<T> = emptyList()): List<T> = value

internal fun TupleTypeDelegate(value: TupleType = emptyTuple()): TupleType = value

internal fun <K, V> DictionaryTypeDelegate(value: Map<K, V> = emptyMap()): Map<K, V> = value

internal fun StringTypeDelegate(value: StringType = ""): StringType = value

internal fun BooleanTypeDelegate(value: BooleanType = false): BooleanType = value