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

package org.morfly.airin.starlark.elements


/**
 * A position mode defines the way how a syntax tree element should be positioned in relation to other elements.
 */
enum class PositionMode {

    /**
     * The element starts completely from a new line.
     *
     * The same indentation applies for each line of the formatted representation of a syntax tree element.
     *
     * For example:
     * ```
     * LIST = [
     *      "item1",
     *      "item2"
     * ]
     * ```
     * Each item of the list is positioned in the NEW_LINE mode as they start from the new line.
     */
    NEW_LINE,

    /**
     * The element continues the line where the previous element of a syntax tree takes place.
     *
     * The same indentation applies for each line of the formatted representation of the element except the first line
     * which is not indented at all.
     *
     * For example:
     * ```
     * LIST = [
     *      "item1",
     *      "item2"
     * ]
     * ```
     * The list expression is positioned in the CONTINUE_LINE mode as it continues the line that is already occupied by
     * the previous element. The indentation in such case should not be applied to the first line of the list expression.
     */
    CONTINUE_LINE,

    /**
     * TODO
     */
    SINGLE_LINE
}