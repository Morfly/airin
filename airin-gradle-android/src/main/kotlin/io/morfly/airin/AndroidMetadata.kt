package io.morfly.airin

import java.io.Serializable

data class AndroidMetadata(
    val applicationId: String?,
    val packageName: String?,
    val composeEnabled: Boolean
): Serializable {

    companion object {
        const val ID = "androidMetadata"
    }
}

var GradleProject.androidMetadata: AndroidMetadata?
    get() = properties[AndroidMetadata.ID] as? AndroidMetadata
    set(value) {
        properties[AndroidMetadata.ID] = value
    }