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

@file:Suppress("SpellCheckingInspection", "unused", "FunctionName")

package template


fun bazelrc_template(
    /**
     *
     */
) = """
    build --java_toolchain //:java_toolchain
    build --host_java_toolchain //:java_toolchain
    
    # Enable d8 merger
    build --define=android_dexmerger_tool=d8_dexmerger
    
    # Flags for the D8 dexer
    build --define=android_incremental_dexing_tool=d8_dexbuilder
    build --define=android_standalone_dexing_tool=d8_compat_dx
    build --nouse_workers_with_dexbuilder
    
    mobile-install --start_app
""".trimIndent()