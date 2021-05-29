package com.morfly.airin.starlark

import com.morlfy.airin.starlark.elements.BazelFile
import com.morlfy.airin.starlark.format.BazelFileFormatter
import com.morlfy.airin.starlark.writer.Writer


class TestWriter : Writer<Nothing?, BazelFile, String> {

    private val formatter = BazelFileFormatter

    override fun write(destination: Nothing?, content: BazelFile): String =
        formatter.format(content)
}