package com.morfly.airin.starlark

import com.morlfy.airin.starlark.elements.BuildFile
import com.morlfy.airin.starlark.lang.BUILD
import com.morlfy.airin.starlark.lang.bazel


class BuildContextCompilationTest {
    val file1: BuildFile = BUILD {
        space
    }

    val file2: BuildFile = BUILD(relativePath = "relative/path") {
        space
    }

    val file3: BuildFile = BUILD.bazel {
        space
    }

    val file4: BuildFile = BUILD.bazel(relativePath = "relative/path") {
        space
    }
}