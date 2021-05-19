package com.morlfy.airin.starlark.writer


/**
 *
 */
interface Writer<D, C> {

    /**
     *
     */
    fun write(destination: D, content: C)
}