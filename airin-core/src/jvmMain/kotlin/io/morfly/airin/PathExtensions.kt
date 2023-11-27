/*
 * Copyright 2023 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
