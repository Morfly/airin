package com.morfly.airin.playground


object In

infix fun <T> Any.`in`(any: List<T>) = In

operator fun <T> List<T>.invoke(body: (T) -> Unit) = listOf<T>()

infix fun In.`if`(condition: String) {

}

infix fun In.`if`(condition: (String) -> String) {

}

fun java_library(body: () -> Unit) {

}

fun test() {
    val DEPS = listOf<String>()


    "name" `in` DEPS {
        java_library { }
    }


    "name" `in` DEPS {
        java_library { }

    } `if` "a > b"


    "name" `in` DEPS {
        java_library { }

    } `if` { "$it > b" }


}