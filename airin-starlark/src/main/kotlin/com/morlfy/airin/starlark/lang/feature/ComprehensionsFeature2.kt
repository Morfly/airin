@file:Suppress("ClassName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.lang.StringType

class ComprehensionContext : ListComprehensionsFeature2

internal interface ListComprehensionsFeature2 : LanguageFeature {

    @JvmInline
    value class _VariableName(val name: String)

    fun `for`(name: String): _VariableName =
        _VariableName(name)

    sealed interface _ComprehensionBuilder {
        val variable: Reference
        val clauses: MutableList<Comprehension.Clause>
    }

    class _CompWithStringItems(
        override val variable: StringReference,
        override val clauses: MutableList<Comprehension.Clause>
    ) : _ComprehensionBuilder

//    class _CompWithListItems<T>(
//        override val variable: ListReference<T>,
//        override val clauses: MutableList<Comprehension.Clause>
//    ) : _ComprehensionBuilder

    class _CompWithListItems<out T>(
        override val variable: ListReference<T>,
        override val clauses: MutableList<Comprehension.Clause>
    ) : _ComprehensionBuilder


    infix fun <T> String.`in`(iterable: List<T>): _CompWithStringItems {
        val itemVariable = StringReference(name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), Expression(iterable))
        )
        return _CompWithStringItems(itemVariable, clauses)
    }

//    infix fun <T, L : List<T>> String.`in`(iterable: List<L>): _CompWithListItems<T> {
//        val itemVariable = ListReference.fromList(iterable.firstOrNull(), name = this)
//        val clauses = mutableListOf<Comprehension.Clause>(
//            Comprehension.For(variables = listOf(itemVariable), Expression(iterable))
//        )
//        return _CompWithListItems(itemVariable, clauses)
//    }

    infix fun <T, L : List<T>> String.`in`(iterable: List<L>): _CompWithListItems<T> {
        val itemVariable = ListReference.fromList(iterable.firstOrNull(), name = this)
        val clauses = mutableListOf<Comprehension.Clause>(
            Comprehension.For(variables = listOf(itemVariable), Expression(iterable))
        )
        return _CompWithListItems(itemVariable, clauses)
    }


    infix fun <T> _CompWithStringItems.`if`(condition: String): _CompWithStringItems {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }

    infix fun <T> _CompWithListItems<T>.`if`(condition: String): _CompWithListItems<T> {
        clauses += Comprehension.If(condition = AnyRawExpression(condition))
        return this
    }


    infix fun <T, R> _CompWithListItems<T>.take(body: (ListReference<T>) -> R): ListComprehension<R> {
        val expression = Expression(body(variable))
        return ListComprehension(body = expression, clauses)
    }

    infix fun <R> _CompWithStringItems.take(body: (StringReference) -> R): ListComprehension<R> {
        val expression = body(variable)
        return ListComprehension(body = Expression(expression), clauses)
    }


    infix fun <T> _CompWithListItems<T>.`for`(body: ComprehensionContext.(ListReference<T>) -> ListComprehension<T>): ListComprehension<T> {
        val innerComprehension = ComprehensionContext().body(variable)
        clauses += innerComprehension.clauses

        val item = innerComprehension.firstOrNull()
        return if (item is ListComprehension<*>) {
            ListComprehension.ofType(item.firstOrNull() as T, innerComprehension, clauses)
        } else ListComprehension(body = innerComprehension, clauses)
//        return ListComprehension(body = innerComprehension, clauses)
    }
}

private interface TestFeature : ListComprehensionsFeature2, AssignmentsFeature, ReassignmentsFeature {

}

private fun TestFeature.display() {
    val MATRIX3 = listOf(listOf(listOf("" as StringType)))
//    val MATRIX = listOf(listOf("" as StringType))
    val MATRIX by listOf(listOf("" as StringType))
    val LIST = listOf("")

    val result1: List<List<StringType>> = "sublist" `in` MATRIX take { it }
    val result2: List<StringType> = "sublist" `in` MATRIX take { "" }
    val result3: List<StringType> = "item" `in` LIST take { it }


    val result4: List<StringType> = "sublist" `in` MATRIX `for` { sublist ->
        "item" `in` sublist take { item ->
            item
        }
    }

//    val result5: List<StringType> = "sublist" `in` MATRIX3 `for` { sublist ->
//        "item" `in` sublist `for`` { item ->
//            "item1" `in` item take {
//                it
//            }
//        }
//    }

}
