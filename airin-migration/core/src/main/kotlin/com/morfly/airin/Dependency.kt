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

package com.morfly.airin


/**
 *
 */
interface Dependency

/**
 *
 */
data class MavenArtifact(
    val group: String?,
    val name: String,
    val version: String?
) : Dependency {

    override fun toString(): String =
        toString(includeVersion = true)

    fun toString(includeVersion: Boolean): String {
        val g = if (group != null) "$group:" else ""
        val v = if (includeVersion && version != null) ":$version" else ""
        return "$g$name$v"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MavenArtifact

        if (group != other.group) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = group?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }
}

/**
 *
 */
fun MavenArtifact(artifact: String): MavenArtifact {
    val components = artifact.split(":")
    if (components.size !in 2..3)
        error("Invalid artifact.")
    return MavenArtifact(
        group = components[0],
        name = components[1],
        version = components.getOrNull(2)
    )
}

/**
 *
 */
data class ProjectModule(
    val relativePath: String,
    val label: String?
) : Dependency