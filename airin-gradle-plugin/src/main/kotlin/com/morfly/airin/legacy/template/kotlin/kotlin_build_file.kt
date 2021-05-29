//package com.morfly.airin.legacy.template.kotlin
//
//import org.morfly.bazelgen.generator.dsl.BUILD
//import org.morfly.bazelgen.generator.dsl.bazel
//import org.morfly.bazelgen.generator.dsl.function.glob
//import org.morfly.bazelgen.generator.dsl.function.kt_jvm_library
//
//
//fun kotlin_build_file(
//    relativePath: String,
//    moduleName: String,
//    /**
//     *
//     */
//) = BUILD.bazel {
//
//    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")
//
//    kt_jvm_library {
//        name = moduleName
//        srcs = glob(
//            "$relativePath/src/main/java/**/*.kt",
//            "$relativePath/src/main/java/**/*.java",
//            "$relativePath/src/main/kotlin/**/*.kt",
//            "$relativePath/src/main/kotlin/**/*.java",
//        )
//        resources = glob("$relativePath/src/resources/**")
//        visibility = listOf("//visibility:public")
//    }
//}