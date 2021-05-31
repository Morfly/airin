package org.morfly.airin.starlark

import org.morfly.airin.starlark.elements.StarlarkFile
import org.morfly.airin.starlark.format.StarlarkFileFormatter
import org.morfly.airin.starlark.writer.Writer


class TestWriter : Writer<Nothing?, StarlarkFile, String> {

    private val formatter = StarlarkFileFormatter

    override fun write(destination: Nothing?, content: StarlarkFile): String =
        formatter.format(content)
}