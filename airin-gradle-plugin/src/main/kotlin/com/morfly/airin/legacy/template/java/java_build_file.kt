//package com.morfly.airin.legacy.template.java
//
//import org.morfly.bazelgen.generator.dsl.BUILD
//import org.morfly.bazelgen.generator.dsl.bazel
//import org.morfly.bazelgen.generator.dsl.function.glob
//import org.morfly.bazelgen.generator.dsl.function.java_library
//
//
///**
// *
// */
//fun java_build_file(
//    relativePath: String,
//    moduleName: String,
//    /**
//     *
//     */
//) = BUILD.bazel(relativePath) {
//
//    load("@rules_java//java:defs.bzl", "java_library")
//
//    java_library {
//        name = moduleName
//        srcs = glob("$relativePath/src/main/java/**/*.java")
//        resources = glob("$relativePath/src/resources/**")
//        visibility = listOf("//visibility:public")
//    }
//}