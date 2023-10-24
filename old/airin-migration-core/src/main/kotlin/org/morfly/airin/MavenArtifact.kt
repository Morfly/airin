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

@file:Suppress("MemberVisibilityCanBePrivate")

package org.morfly.airin


/**
 *
 */
data class MavenArtifact(
    val group: String?,
    val name: String,
    val version: String?
) : Dependency, Labeled, BazelLabeled {

    private val bazelLabelRegex = Regex("[^A-Za-z0-9]")

    override val label = label(version = true)
    override val shortLabel = label(version = false)
    override val defaultLabelFormat = BazelLabelFormat.FullRepo("maven")

    fun label(version: Boolean): String {
        val g = if (group != null) "$group:" else ""
        val v = if (version && this.version != null) ":${this.version}" else ""
        return "$g$name$v"
    }

    override fun bazelLabel(format: BazelLabelFormat): String = buildString {
        val repoName = format.repoName ?: defaultLabelFormat.repoName
        append("@$repoName")

        append("//:${bazelLabelRegex.replace(shortLabel, "_")}")
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
    if (components.isEmpty()) error("Invalid artifact")

    return when (components.size) {
        1 -> MavenArtifact(group = null, name = components[0], version = null)
        2 -> MavenArtifact(group = components[0], name = components[1], version = null)
        3 -> MavenArtifact(group = components[0], name = components[1], version = components[2])
        else -> MavenArtifact(
            group = components[0],
            name = components[1],
            version = components
                .subList(fromIndex = 2, toIndex = components.size)
                .joinToString(separator = ":")
        )
    }
}