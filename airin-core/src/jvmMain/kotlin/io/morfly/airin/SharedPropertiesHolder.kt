package io.morfly.airin

interface SharedPropertiesHolder {

    val sharedProperties: MutableMap<String, Any?>

    val sharedPropertiesAvailable: Boolean
}

//@Suppress("UNCHECKED_CAST")
//val MutableMap<String, Any?>.allMavenArtifacts: MutableSet<MavenCoordinates>
//    get() {
//        val artifacts = getOrPut("allMavenArtifacts") { mutableSetOf<MavenCoordinates>() }
//        return artifacts as MutableSet<MavenCoordinates>
//    }
