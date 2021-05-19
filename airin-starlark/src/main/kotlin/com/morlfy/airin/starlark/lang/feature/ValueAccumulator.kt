package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.ValueHolder


@Suppress("unused")
@JvmInline
value class ValueAccumulator<T>(internal val holder: ValueHolder)