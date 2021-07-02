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

package org.morfly.airin.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import org.morfly.airin.CurlyFunctionGenerator
import org.morfly.airin.FileGeneratorImpl
import org.morfly.airin.FunctionScopeResolverImpl
import org.morfly.airin.RoundFunctionGenerator


class LibraryProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val logger = environment.logger
        val scopeResolver = FunctionScopeResolverImpl()
        return LibraryGenerator(
            options = environment.options,
            fileGenerator = FileGeneratorImpl(
                codeGenerator = environment.codeGenerator,
                functionGenerators = listOf(
                    RoundFunctionGenerator(scopeResolver, logger),
                    CurlyFunctionGenerator(scopeResolver, logger)
                ),
                logger = logger
            ),
            typeValidator = TypeValidatorImpl(),
            logger = logger
        )
    }

}