//package com.morfly.airin.legacy.template.kotlin
//
//import org.morfly.bazelgen.generator.dsl.WORKSPACE
//import org.morfly.bazelgen.generator.dsl.feature.ref
//import org.morfly.bazelgen.generator.dsl.function.http_archive
//import org.morfly.bazelgen.generator.dsl.function.kotlin_repositories
//import org.morfly.bazelgen.generator.dsl.function.kt_register_toolchains
//import kotlin.reflect.KProperty
//
//
//
//
//
//fun kotlin_workspace_file(
//    /**
//     *
//     */
//) = WORKSPACE {
//    load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
//
//    "rules_kotlin_version" `=` "legacy-1.3.0"
//    "rules_kotlin_sha" `=` "4fd769fb0db5d3c6240df8a9500515775101964eebdf85a3f9f0511130885fde"
//    http_archive {
//        name = "io_bazel_rules_kotlin"
//        urls = listOf("https://github.com/bazelbuild/rules_kotlin/archive/%s.zip" `%` "rules_kotlin_version".ref())
//        type = "zip"
//        strip_prefix = "rules_kotlin-%s" `%` "rules_kotlin_version".ref()
//        sha256 = "rules_kotlin_sha"
//    }
//
//    load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")
//    kotlin_repositories {}
//    kt_register_toolchains()
//}