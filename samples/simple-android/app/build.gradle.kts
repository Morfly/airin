plugins {
    alias(sampleLibs.plugins.sample.android.application)
    alias(sampleLibs.plugins.sample.android.compose)
    alias(sampleLibs.plugins.hilt.android)
    alias(sampleLibs.plugins.ksp)
}

android {
    namespace = "io.morfly.airin.sample"

    defaultConfig {
        applicationId = "io.morfly.airin.sample"
    }
}

dependencies {
    implementation(projects.featureA)
    implementation(projects.featureB)
    implementation(projects.libraryA)
    implementation(projects.libraryB)

    implementation(sampleLibs.androidx.core)
    implementation(sampleLibs.androidx.lifecycle.runtime)
    implementation(sampleLibs.compose.activity)
    implementation(sampleLibs.compose.navigation)
    implementation(sampleLibs.hilt.android)
    ksp(sampleLibs.hilt.android.compiler)

    testImplementation(sampleLibs.junit)
}