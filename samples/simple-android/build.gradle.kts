import io.morfly.airin.feature.AndroidToolsFeature
import io.morfly.airin.feature.JetpackComposeFeature
import io.morfly.airin.module.RootModule

plugins {
    alias(sampleLibs.plugins.kotlin.android) apply false
    alias(sampleLibs.plugins.kotlin.jvm) apply false
    alias(sampleLibs.plugins.android.application) apply false
    alias(sampleLibs.plugins.android.library) apply false
    id("io.morfly.airin.android") version sampleLibs.versions.airin
}

airin {
    register<RootModule> {
        include<AndroidToolsFeature>()
        include<JetpackComposeFeature>()
    }
}