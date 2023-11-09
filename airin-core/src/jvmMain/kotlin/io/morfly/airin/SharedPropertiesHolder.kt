package io.morfly.airin

import io.morfly.airin.label.MavenCoordinates

interface SharedPropertiesHolder {

    val sharedProperties: MutableMap<String, Any>
}

@Suppress("UNCHECKED_CAST")
val SharedPropertiesHolder.allMavenArtifacts: MutableSet<MavenCoordinates>
    get() = sharedProperties.getOrPut("allMavenArtifacts") {
        mutableSetOf<MavenCoordinates>()
    } as MutableSet<MavenCoordinates>
