@file:Suppress("ClassName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.StringType


/**
 *
 */
internal interface ListComprehensionsFeature : LanguageFeature {

    // ==== String items =====

    /**
     *
     */
    class _CompWithStringItems(
        internal val variable: StringReference,
        internal val clauses: MutableList<Comprehension.Clause>
    )

    /**
     *
     */
    infix fun String.`in`(iterable: List<StringType>): _CompWithStringItems {
        val itemVariable = StringReference(name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), iterable = Expression(iterable))
        )
        return _CompWithStringItems(itemVariable, clauses)
    }

    /**
     *
     */
    infix fun _CompWithStringItems.`if`(condition: String): _CompWithStringItems {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }

    /**
     *
     */
    infix fun <R> _CompWithStringItems.take(body: (StringReference) -> R): ListComprehension<R> {
        val expression = body(variable)
        return ListComprehension(body = Expression(expression), clauses)
    }

    // ===== List items =====

    /**
     *
     */
    class _CompWithListItems<T>(
        internal val variable: ListReference<T>,
        internal val clauses: MutableList<Comprehension.Clause>
    )

    /**
     *
     */
    infix fun <T> String.`in`(iterable: List<List<T>>): _CompWithListItems<T> {
        val itemVariable = ListReference<T>(name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), Expression(iterable))
        )
        return _CompWithListItems(itemVariable, clauses)
    }


    /**
     *
     */
    infix fun <T> _CompWithListItems<T>.`if`(condition: String): _CompWithListItems<T> {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }

    /**
     *
     */
    infix fun <T, R> _CompWithListItems<T>.`for`(body: (ListReference<T>) -> ListComprehension<R>): ListComprehension<R> {
        val innerComprehension = body(variable)
        clauses += innerComprehension.clauses
        return ListComprehension(body = innerComprehension.body, clauses)
    }

    /**
     *
     */
    infix fun <T, R> _CompWithListItems<T>.take(body: (ListReference<T>) -> List<R>): ListComprehension<List<R>> {
        val expression = body(variable)
        return ListComprehension(body = Expression(expression), clauses)
    }


    // ===== String list items =====

    /**
     *
     */
//    class _CompWithStringListItems(
//        override val variable: ListReference<StringType>,
//        override val clauses: MutableList<Comprehension.Clause>
//    ) : _ComprehensionBuilder
//
//    /**
//     *
//     */
//    infix fun String.`in`(iterable: List<List<StringType>>): _CompWithStringListItems {
//        val itemVariable = ListReference.fromList(iterable.firstOrNull(), name = this)
//        val clauses = mutableListOf<Comprehension.Clause>(
//            Comprehension.For(variables = listOf(itemVariable), Expression(iterable))
//        )
//        return _CompWithStringListItems(itemVariable, clauses)
//    }
//
//    /**
//     *
//     */
//    infix fun _CompWithStringListItems.`if`(condition: String): _CompWithStringListItems {
//        clauses += Comprehension.If(condition = AnyRawExpression(condition))
//        return this
//    }
//
//    /**
//     *
//     */
//    infix fun <R> _CompWithStringListItems.`for`(body: (ListReference<StringType>) -> ListComprehension<R>): ListComprehension<R> {
//        val innerComprehension = body(variable)
//        clauses += innerComprehension.clauses
//        return ListComprehension(body = innerComprehension, clauses)
//    }
//
//    /**
//     *
//     */
//    infix fun <R> _CompWithStringListItems.take(body: (ListReference<StringType>) -> List<R>): ListComprehension<List<R>> {
//        val expression = body(variable)
//        return ListComprehension(body = Expression(expression), clauses)
//    }
}

internal fun ListComprehensionsFeature.test() {
    val LIST = listOf("")
    val result1: List<StringType> = "name" `in` LIST `if` "condition" take { name: StringType -> name }

    val MATRIX = listOf(listOf(""))

    val result3: List<List<StringType>> =
        "sublist" `in` MATRIX `if` "condition" `for` { sublist: ListReference<StringType> ->
            "item" `in` sublist `if` "" take { item ->
                sublist
            }
        }

    val result4: List<StringType> = "sublist" `in` MATRIX `if` "condition" `for` { sublist: ListReference<StringType> ->
        "item" `in` sublist `if` "" take { item: StringType ->
            item
        }
    }


    val MATRIX3 = listOf(listOf(listOf("")))

    val result5: List<StringType> =
        "sublist" `in` MATRIX3 `if` "condition" `for` { sublist2: ListReference<List<StringType>> ->
            "sublist" `in` sublist2 `if` "" `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist take { item: StringType ->
                    item
                }
            }
        }

    val result6: List<List<StringType>> =
        "sublist" `in` MATRIX3 `if` "condition" `for` { sublist2: ListReference<List<StringType>> ->
            "sublist" `in` sublist2 `if` "" `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist take { item: StringType ->
                    sublist
                }
            }
        }

    val result7: List<List<StringType>> =
        "sublist" `in` MATRIX3 `if` "condition" `for` { sublist2: ListReference<List<StringType>> ->
            "sublist" `in` sublist2 `if` "condition" take { sublist: ListReference<StringType> ->
                sublist
            }
        }


    val result8: List<List<List<StringType>>> =
        "sublist" `in` MATRIX3 `if` "condition" take { sublist2: ListReference<List<StringType>> ->
            sublist2
        }

    val result9: List<List<StringType>> =
        "sublist" `in` MATRIX3 `if` "condition" `for` { sublist2: ListReference<List<StringType>> ->
            "sublist" `in` sublist2 `if` "condition" `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist take { item: StringType ->
                    sublist
                }
            }
        }
}