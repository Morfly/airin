package com.morlfy.airin.starlark.format

import com.morlfy.airin.starlark.elements.BazelFile


/**
 *
 */
interface BazelFileFormatter {

    /**
     *
     */
    fun format(bazelFile: BazelFile): String

    /**
     *
     */
    fun format(bazelFile: BazelFile, accumulator: Appendable)

    /**
     *
     */
    companion object Default : BazelFileFormatter by StarlarkCodeFormatter()
}