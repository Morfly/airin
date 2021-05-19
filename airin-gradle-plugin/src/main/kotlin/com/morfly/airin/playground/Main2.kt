package com.morfly.airin.playground//import kotlin.reflect.KProperty
//
//
//
//fun java_library(body: () -> Unit) {
//
//}
//
//
//fun <T> `for`(body: T.() -> T) {
//
//}
//fun test() {
//
//
//
//    var DEPS by listOf("")
//
//    `for` {
//
//        java_library {
//
//        }
//
//        "file" `in` DEPS `if` "a > b"
//        ""
//    }
//    val s = ""
//    {
//
//    }
//}
//
//operator fun <T> List<T>.invoke(body: () -> Unit): List<T> {
//    return emptyList()
//}
//
//operator fun <T> List<T>.getValue(thisRef: Any?, property: KProperty<*>): List<T> {
//    return emptyList()
//}
//
//operator fun <T> List<T>.setValue(thisRef: Any?, property: KProperty<*>, value: List<T>) {
//    println("$value has been assigned to '${property.name}' in $thisRef.")
//}