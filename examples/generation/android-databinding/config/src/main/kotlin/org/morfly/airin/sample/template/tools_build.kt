/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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