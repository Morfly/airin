package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.Key
import com.morlfy.airin.starlark.lang.StarlarkContext
import com.morlfy.airin.starlark.lang.StringType
import com.morlfy.airin.starlark.lang.Value


/**
 *
 */
internal interface FunctionsFeature : LanguageFeature, StatementsHolder {

    /**
     * Dynamic function with args call.
     */
    operator fun String.invoke(body: FunctionCallContext.() -> Unit) {
        val args = FunctionCallContext().apply(body).args
        functionCallStatement(name = this, args)
    }

    /**
     *
     */
    operator fun String.invoke() {
        functionCallStatement(name = this)
    }
}

/**
 *
 */
internal fun FunctionsFeature.functionCallStatement(name: String, args: Set<Argument> = emptySet()) {
    statements += AnyFunctionCall(name, args)
}

/**
 *
 */
internal fun FunctionsFeature.functionCallStatement(name: String, args: Map<String, Any?>) {
    functionCallStatement(name, Arguments(args))
}

/**
 *
 */
internal inline fun <T : FunctionCallContext> FunctionsFeature.functionCallStatement(
    name: String, context: T, body: T.() -> Unit
) {
    val args = context.apply(body).args
    functionCallStatement(name, args)
}

/**
 *
 */
// TODO use kotlin reflection instead of java
// TODO document usage of StarlarkContext instead of FunctionsFeature
inline fun <reified T> StarlarkContext.functionCallExpression(name: String, args: Set<Argument> = emptySet()): T =
    when {
        CharSequence::class.java.isAssignableFrom(T::class.java) -> StringFunctionCall(name, args)
        List::class.java.isAssignableFrom(T::class.java) -> ListFunctionCall<Any?>(name, args)
        Map::class.java.isAssignableFrom(T::class.java) -> DictionaryFunctionCall<Key, Value>(name, args)
        else -> AnyFunctionCall(name, args)
    } as T

/**
 *
 */
inline fun <reified T> StarlarkContext.functionCallExpression(name: String, args: Map<String, Any?>): T =
    functionCallExpression(name, Arguments(args))


/**
 *
 */
@LanguageFeatureContext
open class FunctionCallContext internal constructor() : BinaryPlusFeature, DynamicBinaryPlusFeature {

    val args = linkedSetOf<Argument>()

    // TODO make arg nullable
    infix fun String.`=`(value: StringType): ValueAccumulator<StringType> {
        val argument = Argument(id = this, value = Expression(value))
        args += argument
        return ValueAccumulator(argument)
    }

    // TODO consider replacing T with Any?
    infix fun String.`=`(value: List<Value>): ValueAccumulator<List<Value>> {
        val argument = Argument(id = this, value = Expression(value))
        args += argument
        return ValueAccumulator(argument)
    }

    infix fun String.`=`(value: Map<Key, Value>): ValueAccumulator<Map<Key, Value>> {
        val argument = Argument(id = this, value = Expression(value))
        args += argument
        return ValueAccumulator(argument)
    }

    infix fun String.`=`(body: DictionaryContext.() -> Unit): ValueAccumulator<Map<Key, Value>> {
        val value = DictionaryContext().apply(body).dictionary
        val argument = Argument(id = this, value = DictionaryExpression(value))
        return ValueAccumulator(argument)
    }

//    infix fun String.`=`(value: Any?): ValueAccumulator<Any> {
//        val argument = Argument(id = this, value = Expression(value))
//        args += argument
//        return ValueAccumulator(argument)
//    }

    infix fun String.`=`(value: Any?) {
        val argument = Argument(id = this, value = Expression(value))
        args += argument
    }

//    infix fun <T> String.`=`(value: T?): ValueAccumulator<T?> {
//        val argument = Argument(id = this, value = Expression(value))
//        args += argument
//        return ValueAccumulator(argument)
//    }
}

internal fun FunctionsFeature.test() {
    "my_function" {
        "arg1" `=` listOf("") `+` null `+` listOf(1)
        "arg2" `=` "" `+` null
//        "arg2" `=` null `+` "" TODO
        "arg2" `=` listOf() //`+` ""
        "arg2" `=` listOf("") `+` listOf("")
        "arg3" `=` mapOf("" to "") `+` mapOf("" to 1)

        "arg3" `=` { "arg" to "value" } `+` mapOf("" to "")

        "arg3" `=` mapOf("" to "") `+` { "arg" to "value" } `+` mapOf("" to "")
    }
    "my_function"()
}