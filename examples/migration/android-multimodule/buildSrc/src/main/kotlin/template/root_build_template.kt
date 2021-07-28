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