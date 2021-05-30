@file:Suppress("FunctionName")

package org.morfly.example.template

import com.morlfy.airin.starlark.lang.BUILD
import com.morlfy.airin.starlark.library.java_import
import com.morlfy.airin.starlark.library.java_plugin


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