plugins {
    alias(sampleLibs.plugins.sample.android.application)
    alias(sampleLibs.plugins.sample.android.compose)
}

android {
    namespace = "io.morfly.airin.sample"

    defaultConfig {
        applicationId = "io.morfly.airin.sample"
    }
}

dependencies {
    implementation(sampleLibs.androidx.core)
    implementation(sampleLibs.androidx.lifecycle.runtime)
    implementation(sampleLibs.compose.activity)

    testImplementation(sampleLibs.junit)
}