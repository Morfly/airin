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

package template

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.bazel
import org.morfly.airin.starlark.library.glob
import org.morfly.airin.starlark.library.java_import


fun root_build_template(
    /**
     *
     */
) = BUILD.bazel {
    load("@dagger//:workspace_defs.bzl", "dagger_rules")

    "dagger_rules"()

    java_import(
        name = "kotlin_coroutines_jvm",
        jars = glob("third_party/coroutines/*.jar"),
        visibility = list["//visibility:public"]
    )
}