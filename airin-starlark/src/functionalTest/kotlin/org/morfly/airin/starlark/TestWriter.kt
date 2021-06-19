/*
 * Copyright 2021 Pavlo Stavytskyi
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

package org.morfly.airin.starlark

import org.morfly.airin.starlark.elements.StarlarkFile
import org.morfly.airin.starlark.format.StarlarkFileFormatter
import org.morfly.airin.starlark.writer.Writer


class TestWriter : Writer<Nothing?, StarlarkFile, String> {

    private val formatter = StarlarkFileFormatter

    override fun write(destination: Nothing?, content: StarlarkFile): String =
        formatter.format(content)

    fun write(content: StarlarkFile): String =
        write(destination = null, content)
}