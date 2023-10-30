import io.morfly.airin.feature.AndroidBinaryFeature
import io.morfly.airin.feature.AndroidToolsFeature
import io.morfly.airin.feature.ForcedMavenArtifactsFeature
import io.morfly.airin.feature.HiltAndroidFeature
import io.morfly.airin.feature.JetpackComposeFeature
import io.morfly.airin.module.AndroidLibraryModule
import io.morfly.airin.module.RootModule

plugins {
    alias(sampleLibs.plugins.kotlin.android) apply false
    alias(sampleLibs.plugins.kotlin.jvm) apply false
    alias(sampleLibs.plugins.android.application) apply false
    alias(sampleLibs.plugins.android.library) apply false
    alias(sampleLibs.plugins.ksp) apply false
    alias(sampleLibs.plugins.hilt.android) apply false

    id("io.morfly.airin.android") version sampleLibs.versions.airin
}

// If modifying components don't forget to run ./gradlew publishToMavenLocal --no-configuration-cache
// Run ./gradlew simple-android:migrateToBazel to generate Bazel files.
airin {
    register<AndroidLibraryModule> {
        include<AndroidBinaryFeature>()
        include<JetpackComposeFeature>()
        include<HiltAndroidFeature>()
    }
    register<RootModule> {
        include<AndroidToolsFeature>()

        // When a conflict with transitive dependency versions happens, Gradle and Bazel resolve
        // them differently. Where Gradle resolves them automatically for the most part, Bazel
        // requires to explicitly specify the artifact version that should be resolved.
        include<ForcedMavenArtifactsFeature> {
            artifacts = listOf(
                "androidx.compose.compiler:compiler:1.4.7",
                "androidx.lifecycle:lifecycle-runtime:2.6.1",
                "androidx.activity:activity-ktx:1.7.0",
                "androidx.compose.animation:animation-core:1.2.1",
                "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1",
            )
        }
    }
}
