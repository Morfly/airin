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

package org.morfly.airin

import org.morfly.airin.starlark.elements.StarlarkFile
import org.gradle.api.Project


/**
 *
 */
class GradleTemplateProvidersHolder(
    providers: List<GradleTemplateProvider>
) {
    private val uniqueProviders = mutableSetOf<Class<out GradleTemplateProvider>>()

    private val providers = providers.groupBy {
        uniqueProviders += it::class.java
        it.id
    }

    /**
     *
     */
    fun providePerModule(target: Project, relativePath: String): List<StarlarkFile> =
        providers[GradlePerModuleTemplateProvider]!!
            .firstOrNull { it.canProvide(target) }
            ?.provide(target, relativePath)
            ?: emptyList()

    /**
     *
     */
    fun provideStandalone(root: Project): List<StarlarkFile> =
        providers[GradleStandaloneTemplateProvider]!!
            .filter { it.canProvide(root) }
            .flatMap { it.provide(root, relativePath = "") }

    /**
     *
     */
    operator fun contains(cls: Class<out GradleTemplateProvider>): Boolean =
        cls in uniqueProviders
}