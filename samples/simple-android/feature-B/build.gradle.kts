plugins {
    alias(sampleLibs.plugins.sample.android.library)
    alias(sampleLibs.plugins.sample.android.compose)
    alias(sampleLibs.plugins.hilt.android)
    alias(sampleLibs.plugins.ksp)
}

android {
    namespace = "io.morfly.airin.sample.featureB"
}

dependencies {
    implementation(sampleLibs.compose.navigation)
    implementation(sampleLibs.compose.navigation.hilt)
    implementation(sampleLibs.hilt.android)
    ksp(sampleLibs.hilt.android.compiler)
}