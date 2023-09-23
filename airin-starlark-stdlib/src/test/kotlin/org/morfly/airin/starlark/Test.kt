package org.morfly.airin.starlark

import org.morfly.airin.starlark.lang.BUILD
import org.morfly.airin.starlark.lang.bazel

fun test() {
    BUILD.bazel {
        "my_function" {

        }
    }
}