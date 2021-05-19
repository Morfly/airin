package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.elements.BinaryOperator.PLUS
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


internal interface BinaryPlusFeature : LanguageFeature {

    infix fun StringType?.`+`(other: StringType?): StringType =
        StringBinaryOperation(
            left = this?.let(::StringLiteral),
            operator = PLUS,
            right = other?.let(::StringLiteral)
        )

    infix fun <T> List<T>?.`+`(other: List<T>?): List<T> =
        ListBinaryOperation(
            left = this?.let(::ListExpression),
            operator = PLUS,
            right = other?.let(::ListExpression)
        )

    infix fun Map<*, Value>?.`+`(other: Map<*, Value>?): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = this?.let(::DictionaryExpression),
            operator = PLUS,
            right = other?.let(::DictionaryExpression)
        )

    infix fun Map<*, Value>?.`+`(body: DictionaryContext.() -> Unit): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = this?.let(::DictionaryExpression),
            operator = PLUS,
            right = DictionaryExpression(DictionaryContext().apply(body).dictionary)
        )
}


internal interface DynamicBinaryPlusFeature : LanguageFeature {

    infix fun ValueAccumulator<StringType>.`+`(other: StringType?): ValueAccumulator<StringType> {
        holder.value = StringBinaryOperation(
            left = holder.value,
            operator = PLUS,
            right = other?.let(::StringLiteral)
        )
        return this
    }

    infix fun <T> ValueAccumulator<List<T>>.`+`(other: List<T>?): ValueAccumulator<List<T>> {
        holder.value = ListBinaryOperation<T>(
            left = holder.value,
            operator = PLUS,
            right = other?.let(::ListExpression)
        )
        return this
    }

    infix fun <K, V : Value> ValueAccumulator<Map<K, V>>.`+`(other: Map<*, Value>?): ValueAccumulator<Map<K, V>> {
        holder.value = DictionaryBinaryOperation<Key, Value>(
            left = holder.value,
            operator = PLUS,
            right = other?.let(::DictionaryExpression)
        )
        return this
    }

    infix fun <K, V : Value> ValueAccumulator<Map<K, V>>.`+`(body: DictionaryContext.() -> Unit): ValueAccumulator<Map<K, V>> {
        holder.value = DictionaryBinaryOperation<Key, Value>(
            left = holder.value,
            operator = PLUS,
            right = DictionaryExpression(DictionaryContext().apply(body).dictionary)
        )
        return this
    }

    // TODO check if needed
    infix fun ValueAccumulator<Any>.`+`(other: Any?): ValueAccumulator<Any> {
        holder.value = AnyBinaryOperation(
            left = holder.value,
            operator = PLUS,
            right = Expression(other)
        )
        return this
    }
}

internal interface MappingBinaryPlusFeature : LanguageFeature {

    infix fun KeyValuePair.`+`(other: StringType?): KeyValuePair {
        value = StringBinaryOperation(
            left = Expression(value),
            operator = PLUS,
            right = other?.let(::StringLiteral)
        )
        return this
    }
//infix fun ValueAccumulator<StringType>.`+`(other: StringType?): ValueAccumulator<StringType> {
//    holder.value = StringBinaryOperation(
//        left = holder.value,
//        operator = PLUS,
//        right = other?.let(::StringLiteral)
//    )
//    return this
//}
}


internal interface BinaryPlusFeature1 : LanguageFeature {

    /**
     *
     */
    infix fun CharSequence?.`+`(other: CharSequence?): CharSequence =
        StringBinaryOperation(
            left = this?.let(::StringLiteral),
            operator = PLUS,
            right = other?.let(::StringLiteral)
        )

    /**
     *
     */
    infix fun <T> List<T>?.`+`(other: List<T>?): List<T> =
        ListBinaryOperation(
            left = this?.let(::ListExpression),
            operator = PLUS,
            right = other?.let(::ListExpression)
        )

    /**
     *
     */
    infix fun Map<*, Value>?.`+`(other: Map<*, Value>?): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = this?.let(::DictionaryExpression),
            operator = PLUS,
            right = other?.let(::DictionaryExpression)
        )

    /**
     *
     */
    infix fun Map<*, Value>?.`+`(body: DictionaryContext.() -> Unit): Map<Key, Value> =
        DictionaryBinaryOperation(
            left = this?.let(::DictionaryExpression),
            operator = PLUS,
            right = DictionaryExpression(DictionaryContext().apply(body).dictionary)
        )
}

internal fun BinaryPlusFeature.test() {
    listOf(1, 2, 3) `+` listOf(1, 2, 3)
    mapOf("" to "") `+` {}
    mapOf("" to "") `+` mapOf(1 to "")
    val map1 = mapOf("" to "")
    val map2 = mapOf(1 to "")

    fun dict(body: DictionaryContext.() -> Unit): Map<Key, Value> {
        return emptyMap()
    }
    map1 `+` map2 `+` {}
    "null" `+` null
//    Any() `+` null
}

internal fun DynamicBinaryPlusFeature.test() {
    val strValue = ValueAccumulator<StringType>(Argument("name", null))
    strValue `+` ""

    val listValue = ValueAccumulator<List<String>>(Argument("name", null))
    listValue `+` listOf()

    val mapValue = ValueAccumulator<Map<Key, Value>>(Argument("name", null))
    fun dict(body: DictionaryContext.() -> Unit): Map<Key, Value> {
        return emptyMap()
    }
    mapValue `+` emptyMap<Key, Value>()
    mapValue `+` { }
    mapValue `+` dict { }
}