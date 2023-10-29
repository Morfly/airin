import io.morfly.airin.feature.AndroidBinaryFeature
import io.morfly.airin.feature.AndroidToolsFeature
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
    }
    register<RootModule> {
        include<AndroidToolsFeature>()
    }
}