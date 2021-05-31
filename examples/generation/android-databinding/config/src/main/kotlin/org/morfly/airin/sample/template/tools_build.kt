@file:Suppress("FunctionName")

package org.morfly.airin.sample.template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.library.java_import
import org.morfly.airin.starlark.library.java_plugin


fun tools_build(
    /**
     *
     */
) = BUILD("tools/android") {

    java_plugin(
        name = "compiler_annotation_processor",
        processor_class = "android.databinding.annotationprocessor.ProcessDataBinding",
        visibility = list["//visibility:public"],
        generates_api = true,
        deps = list[
                "@bazel_tools//src/tools/android/java/com/google/devtools/build/android:all_android_tools"
        ]
    )

    java_import(
        name = "android_sdk",
        jars = list["@bazel_tools//tools/android:android_jar"],
        neverlink = true,
        visibility = list["//visibility:public"]
    )
}