@file:Suppress("ClassName")

package com.morlfy.airin.starlark.lang.feature

import com.morlfy.airin.starlark.elements.ValueHolder


@Suppress("unused")
@JvmInline
value class _ValueAccumulator<T>(internal val holder: ValueHolder)