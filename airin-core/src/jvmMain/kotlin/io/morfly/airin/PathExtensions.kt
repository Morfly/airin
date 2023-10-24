package io.morfly.airin

fun String.fixPath(): String =
    if (isEmpty()) this
    else fixPathPrefix().fixPathSuffix()

fun String.fixPathPrefix(): String {
    val path = trimStart()
    var i = 0
    while (path.startsWith('/')) i++

    return path.removePrefix("/".repeat(i))
}

fun String.fixPathSuffix(): String {
    val path = trimEnd()
    var i = 0
    while (path.endsWith('/')) i++

    return path.removeSuffix("/".repeat(i))
}
