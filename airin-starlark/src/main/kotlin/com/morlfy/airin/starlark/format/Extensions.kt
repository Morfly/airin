package com.morlfy.airin.starlark.format


/**
 *
 */
internal operator fun Appendable.plusAssign(string: String) {
    append(string)
}

/**
 *
 */
internal operator fun Appendable.plusAssign(char: Char) {
    append(char)
}