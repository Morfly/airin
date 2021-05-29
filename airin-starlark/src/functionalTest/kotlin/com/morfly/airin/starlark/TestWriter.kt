package com.morfly.airin.starlark

import com.morlfy.airin.starlark.elements.StarlarkFile
import com.morlfy.airin.starlark.format.BazelFileFormatter
import com.morlfy.airin.starlark.writer.Writer


class TestWriter : Writer<Nothing?, StarlarkFile, String> {

    private val formatter = BazelFileFormatter

    override fun write(destination: Nothing?, content: StarlarkFile): String =
        formatter.format(content)
}