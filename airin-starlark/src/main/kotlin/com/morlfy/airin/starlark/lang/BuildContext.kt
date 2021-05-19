package com.morlfy.airin.starlark.lang

import com.morlfy.airin.starlark.elements.BuildFile


class BuildContext : StarlarkContext()


inline fun BUILD(relativePath: String = "", body: BuildContext.() -> Unit): BuildFile =
    BuildContext()
        .apply(body)
        .let { BuildFile(hasExtension = false, relativePath, statements = it.statements.toList()) }


object BUILD


inline fun BUILD.bazel(relativePath: String = "", body: BuildContext.() -> Unit): BuildFile =
    BuildContext()
        .apply(body)
        .let { BuildFile(hasExtension = true, relativePath, statements = it.statements.toList()) }