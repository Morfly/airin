package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.Argument
import com.morlfy.airin.starlark.elements.DictionaryExpression
import com.morlfy.airin.starlark.elements.Expression
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
internal interface ArgumentsHolder {

    /**
     *
     */
    val args: LinkedHashSet<Argument>
}

/**
 *
 */
internal interface ArgumentsFeature : LanguageFeature, ArgumentsHolder {

    /**
     *
     */
    infix fun String.`=`(value: StringType): _ValueAccumulator<StringType> {
        val argument = Argument(id = this, value = Expression(value))
        args += argument
        return _ValueAccumulator(argument)
    }

    /**
     *
     */
    infix fun <T> String.`=`(value: List<T>): _ValueAccumulator<List<T>> {
        val argument = Argument(id = this, value = Expression(value))
        args += argument
        return _ValueAccumulator(argument)
    }

    /**
     *
     */
    infix fun <K : Key, V : Value> String.`=`(value: Map<K, V>): _ValueAccumulator<Map<K, V>> {
        val argument = Argument(id = this, value = Expression(value))
        args += argument
        return _ValueAccumulator(argument)
    }

    /**
     *
     */
    infix fun String.`=`(body: DictionaryContext.() -> Unit): _ValueAccumulator<Map<Key, Value>> {
        val value = DictionaryContext().apply(body).kwargs
        val argument = Argument(id = this, value = DictionaryExpression(value))
        args += argument
        return _ValueAccumulator(argument)
    }

    /**
     * handles null and any arg
     */
    infix fun String.`=`(value: Any?): _ValueAccumulator<Any> {
        val argument = Argument(id = this, value = Expression(value))
        args += argument
        return _ValueAccumulator(argument)
    }
}

private fun FunctionCallContext.test() {
    "list" `=` listOf("item1", "item2") `+` listOf("item")
    "map" `=` mapOf("key" to "value") `+` mapOf(1 to 2)
    "map" `=` { "key" to "value" } `+` mapOf(1 to 2)
    "any" `=` null
}