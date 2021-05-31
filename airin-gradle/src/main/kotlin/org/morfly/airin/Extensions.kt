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

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.artifacts.ProjectDependency


/**
 *
 */
val defaultConfigurations = setOf(
    "implementation",
    "api",
)

/**
 *
 */
fun Project.findDependencies(configs: Set<String> = defaultConfigurations): List<Dependency> =
    configurations
        .asSequence()
        .filter { it.name in configs }
        .flatMap { it.dependencies }
        .mapNotNull {
            when (it) {
                is ExternalDependency -> MavenArtifact(it.group, it.name, it.version)
                is ProjectDependency -> ProjectModule(
                    relativePath = rootProject.relativePath(it.dependencyProject.projectDir),
                    label = it.name
                )
                else -> null
            }
        }
        .toList()

/**
 *
 */
fun Project.findArtifactDependencies(configs: Set<String> = defaultConfigurations): List<MavenArtifact> =
    configurations
        .asSequence()
        .filter { it.name in configs }
        .flatMap { it.dependencies }
        .filterIsInstance<ExternalDependency>()
        .map { MavenArtifact(it.group, it.name, it.version) }
        .toList()

/**
 *
 */
fun Project.findModuleDependencies(configs: Set<String> = defaultConfigurations): List<ProjectModule> =
    configurations
        .asSequence()
        .filter { it.name in configs }
        .flatMap { it.dependencies }
        .filterIsInstance<ProjectDependency>()
        .map {
            ProjectModule(
                relativePath = rootProject.relativePath(it.dependencyProject.projectDir),
                label = this.name
            )
        }
        .toList()

/**
 *
 */
fun convertGradleLabelToBazel(gradleLabel: String): String =
    gradleLabel
        .split(":")
        .filter { it.isNotBlank() }
        .joinToString(prefix = "//", separator = "/")

/**
 *
 */
fun ProjectModule.bazelLabel(): String? =
    label?.let(::convertGradleLabelToBazel)