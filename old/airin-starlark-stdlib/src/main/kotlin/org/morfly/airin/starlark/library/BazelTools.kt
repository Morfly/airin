package org.morfly.airin.starlark.library

import org.morfly.airin.starlark.lang.Name
import org.morfly.airin.starlark.lang.api.Argument
import org.morfly.airin.starlark.lang.api.FunctionKind.Statement
import org.morfly.airin.starlark.lang.api.FunctionScope.Build
import org.morfly.airin.starlark.lang.api.LibraryFunction


@LibraryFunction(
    name = "default_java_toolchain",
    scope = [Build],
    kind = Statement
)
private interface DefaultJavaToolchain {

    @Argument(required = true)
    val name: Name
}