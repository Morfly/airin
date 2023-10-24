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
    "kapt",
    "kotlin-extension",
)

/**
 *
 */
fun Project.dependencies(configs: Set<String> = defaultConfigurations): List<Dependency> =
    configurations
        .asSequence()
        .filter { it.name in configs }
        .flatMap { it.dependencies }
        .mapNotNull {
            when (it) {
                is ExternalDependency -> MavenArtifact(it.group, it.name, it.version)
                is ProjectDependency -> Module(
                    relativePath = rootProject.relativePath(it.dependencyProject.projectDir),
                    name = it.dependencyProject.name,
                    originalLabel = it.dependencyProject.path
                )
                else -> null
            }
        }
        .toList()

/**
 *
 */
fun Project.dependencies(vararg configs: String): List<Dependency> =
    dependencies(setOf(*configs))

/**
 *
 */
fun Project.artifactDependencies(configs: Set<String> = defaultConfigurations): List<MavenArtifact> =
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
fun Project.artifactDependencies(vararg configs: String): List<MavenArtifact> =
    artifactDependencies(setOf(*configs))

/**
 *
 */
fun Project.moduleDependencies(configs: Set<String> = defaultConfigurations): List<Module> =
    configurations
        .asSequence()
        .filter { it.name in configs }
        .flatMap { it.dependencies }
        .filterIsInstance<ProjectDependency>()
        .map {
            Module(
                relativePath = rootProject.relativePath(it.dependencyProject.projectDir),
                name = it.dependencyProject.name,
                originalLabel = it.dependencyProject.path
            )
        }
        .toList()

/**
 *
 */
fun Project.moduleDependencies(vararg configs: String): List<Module> =
    moduleDependencies(setOf(*configs))

///**
// *
// */
//fun convertGradleLabelToBazel(gradleLabel: String): String =
//    gradleLabel
//        .split(":")
//        .filter { it.isNotBlank() }
//        .joinToString(prefix = "//", separator = "/")