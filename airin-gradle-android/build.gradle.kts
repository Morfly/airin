plugins {
    `kotlin-dsl`
    alias(libs.plugins.airin.maven.publish)
    alias(libs.plugins.dokka)
}

kotlin {
    jvmToolchain(AirinMetadata.JVM_TOOLCHAIN_VERSION)
}

dependencies {
    implementation(projects.airinCore)
    implementation(projects.airinGradlePlugin)

    implementation(libs.pendant.starlark)
    implementation(libs.pendant.library.bazel)
}

gradlePlugin {
    plugins {
        val airin by registering {
            id = "io.morfly.airin.android"
            implementationClass = "io.morfly.airin.plugin.AirinAndroidGradlePlugin"
        }
    }
}
