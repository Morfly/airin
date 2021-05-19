package com.morfly.airin.playground//package com.morfly.airin
//
//import kotlin.reflect.KProperty
//
//
//object ListOf
//
//fun listOf(body: String.() -> Unit): ListOf {
//    return ListOf
//}
//
//fun java_library(body: Any.() -> Unit) {
//
//}
//
//var Any.name: String
//    get() = TODO()
//    set(value) = TODO()
//
//object For
//
//infix fun ListOf.`for`(name: String): For {
//    return For
//}
//
//infix fun ListOf.`for`(name: List<String>): For {
//    return For
//}
//
//object In
//
//infix fun For.`in`(ref: Any): In {
//    return In
//}
//
//
//fun `for`(name: String, body: () -> Unit): For {
//    return For
//}
//
//
//infix fun In.`if`(anything: Any) {
//
//}
//
//fun main() {
//    var DEPENDENCIES = ""
//    var LIST_DEPS by listOf("")
//
//    listOf {
//
//        java_library {
//
//        }
//
//    } `for` "file" `in` DEPENDENCIES `if` "anything > anything_else"
//
//
//
//
//    `for`("file") {
//
//        java_library {
//
//        }
//
//    } `in` DEPENDENCIES `if` "anything > anything_else"
//
//
//    var file by `for` {
//
//
//        java_library {
//
//        }
//
//    } `in` DEPENDENCIES `if` "anything > anything_else"
//
//    `for` {
//        val file by LIST_DEPS `if` "anything > anything_else"
//
//        java_library {
//
//        }
//    }
//
//    listOf {
//        var file by LIST_DEPS.forEach {
//
//        }
//    }
//
//    var file by LIST_DEPS {
//
//    } `if` "anything > anything_else"
//
//
//    LIST_DEPS `for` "file" {
//
//    } `if` "anything > anything_else"
//
////    `for`("file") `in` LIST_DEPS `if` "anything > anything_else" {
////
////    }
//
////    LIST_DEPS `for` "file"
//
////    listOf {
//////        val file by `in`(DEPENDENCIES)
//////
//////        val file1 in DEPENDENCIES
////
////        `for`(file in DEPENDENCIES) {
////
////        }
////
////        for(file in DEPENDENCIES) {
////            java_library {
////
////            }
////        }
////    }
//}
//
//fun <T> List<T>.`for`(name: String) {
//
//}
//
//object ForDelegate
//
//fun `for`(body: () -> Unit): ForDelegate {
//    return ForDelegate
//}
//
//infix fun Any.`if`(ref: Any): ForDelegate {
//    return ForDelegate
//}
//
//fun `in`(any: Any): ForDelegate {
//    return ForDelegate
//}
//
//infix fun ForDelegate.`in`(ref: Any): ForDelegate {
//    return ForDelegate
//}
//
//infix fun ForDelegate.`if`(ref: Any): ForDelegate {
//    return ForDelegate
//}
//
//operator fun ForDelegate.getValue(thisRef: Any?, property: KProperty<*>): String {
//    return "$thisRef, thank you for delegating '${property.name}' to me!"
//}
//
//operator fun ForDelegate.setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
//    println("$value has been assigned to '${property.name}' in $thisRef.")
//}
//
//
//
//operator fun String?.getValue(thisRef: Any?, property: KProperty<*>): String? {
//    return "$thisRef, thank you for delegating '${property.name}' to me!"
//}
//
//operator fun String?.setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
//    println("$value has been assigned to '${property.name}' in $thisRef.")
//}
//
//operator fun <T> List<T>.getValue(thisRef: Any?, property: KProperty<*>): List<T> {
//    return emptyList()
//}
//
//operator fun <T> List<T>.setValue(thisRef: Any?, property: KProperty<*>, value: List<T>) {
//    println("$value has been assigned to '${property.name}' in $thisRef.")
//}
//
//
