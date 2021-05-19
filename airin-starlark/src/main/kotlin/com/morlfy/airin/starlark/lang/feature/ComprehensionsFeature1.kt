//package com.morlfy.airin.starlark.lang.feature
//
//import com.morlfy.airin.starlark.elements.*
//import com.morlfy.airin.starlark.lang.StringType
//
//
//class ComprehensionContext {
//
//}
//
//object In
//
//infix fun String.`in`(arg: List<*>): In {
//    return In
//}
//
//operator fun In.minus(body: () -> Unit) {
//
//}
//
//infix fun In.`-`(body: () -> Unit) {
//
//}
//
//infix fun In.`do`(body: () -> Unit) {
//
//}
//
//infix fun In.take(body: () -> Unit) {
//
//}
//
//fun test1() {
//    val MATRIX = listOf(listOf(""))
//
//    "sublist" `in` MATRIX take {
//
//    }
//
//    "sublist" `in` MATRIX `if` "condition" take {
//
//    }
//
//    "sublist" `in` MATRIX `for` { sublist ->
//        "var" `in` sublist take { `var` ->
//
//        }
//    }
//
//    "sublist" `in` MATRIX `for` "var" `in`
//
//
////    "sublist" `in` MATRIX `if` "condition" `for` { sublist -> "val" `in` sublist { `val` -> `val` } `if` "condition" }
////
////    "sublist" `in` MATRIX - {
////
////    }
//}
//
//internal interface ComprehensionsFeature : LanguageFeature, StatementsHolder {
//
//    @JvmInline
//    value class _ListComprehensionFor(val name: String)
//
//    class _StringReferenceBody<R>(
//        val iterable: Iterable<StringType>?,
//        val body: ComprehensionContext.(StringReference) -> R?
//    )
//
//    class _ListReferenceBody<T, R>(
//        val iterable: Iterable<List<T>>?,
//        val body: ComprehensionContext.(ListReference<T>) -> R?
//    )
//
//    operator fun <R> Iterable<StringType>.invoke(body: ComprehensionContext.(StringReference) -> R): _StringReferenceBody<R> =
//        _StringReferenceBody(iterable = this, body)
//
//    operator fun <T, R> Iterable<List<T>>.invoke(body: ComprehensionContext.(ListReference<T>) -> R?): _ListReferenceBody<T, R> =
//        _ListReferenceBody(iterable = this, body)
//
//    fun `for`(name: String): _ListComprehensionFor =
//        _ListComprehensionFor(name)
//
//    infix fun <T> String.`in`(body: _StringReferenceBody<T>): ListComprehension<T> {
//        val context = ComprehensionContext()
//        val variable = StringReference(name = this)
//        val bodyExpression = Expression(body.body(context, variable))
//        val comprehension = ListComprehension<T>(
//            body = bodyExpression,
//            clauses = mutableListOf(
//                Comprehension.For(variables = listOf(variable), iterable = Expression(body.iterable))
//            )
//        )
//        statements += comprehension
//        return comprehension
//    }
//
//    infix fun <T, R> String.`in`(body: _ListReferenceBody<T, R>): ListComprehension<R> {
////        val context = ComprehensionContext()
////        val variable = ListReference<T>(name = this)
////        val bodyExpression = Expression(body.body(context, variable))
////        val comprehension = ListComprehension<T>(
////            body = bodyExpression,
////            clauses = mutableListOf(
////                Comprehension.For(variables = listOf(variable), iterable = Expression(body.iterable))
////            )
////        )
////        statements += comprehension
////        return comprehension
//        val context = ComprehensionContext()
//        val variable = ListReference<T>(name = this)
//        return registerListComprehension(
//            variable = variable,
//            body = Expression(body.body(context, variable))
//        )
//    }
//
//    infix fun <T> ListComprehension<T>.`if`(rawCondition: String): ListComprehension<T> {
//        clauses += Comprehension.If(condition = AnyRawExpression(rawCondition))
//        return this
//    }
//
//    fun test() {
//        val LIST = listOf("item")
//        val result1: List<String> = "name" `in` LIST { name ->
//            ""
//        }
//
//        val MATRIX = listOf(listOf(""))
//
//        val result: List<StringType> = "list" `in` MATRIX { list ->
//            ""
////            "item" `in` list { item ->
////                item
////            }
//        }
//
////        `for`("name") `in` MATRIX {
////
////        }
//
//    }
//
//
//}
//
//internal fun <T> ComprehensionsFeature.registerListComprehension(
//    variable: Reference,
//    body: Expression?
//): ListComprehension<T> {
//    val comprehension = ListComprehension<T>(
//        body,
//        clauses = mutableListOf(
//            Comprehension.For(variables = listOf(variable), iterable = body)
//        )
//    )
//    statements += comprehension
//    return comprehension
//}
//
//
////internal fun ComprehensionsFeature.test() {
////    val LIST = listOf("string")
////    val INNER_LIST = listOf("inner")
////    val MATRIX = listOf(listOf("1"))
////
//////    `for`("i") `in` LIST {
//////        `for`("j") `in` INNER_LIST { "j" }
//////    }
////
////    "i" `in` LIST { "j" `in` INNER_LIST { j -> j } }
////
////
////
////    list["i" `in` LIST { list["j" `in` INNER_LIST { j -> j }] }]
////
////
//////    + list["i" `in` LIST { i -> i }]
////
////    list[
////            "file" `in` LIST { file ->
//////                rule(
//////                    name = file
//////                )
////            }
////    ] + listOf("", "")
////
////
//////    dict {
//////        "i" `in` LIST {
//////            "j" `in` INNER_LIST { j -> j }
//////        }
//////    }
////
//////    "sublist" `in` LIST `for` "val" `in` INNER_LIST {
//////
//////    }
////    `for`("sublist") `in` MATRIX { sublist ->
////        `for`("value") `in` sublist { value -> }
////    }
////}