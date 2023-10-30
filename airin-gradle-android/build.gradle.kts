plugins {
    `kotlin-dsl`
    alias(libs.plugins.airin.maven.publish)
    alias(libs.plugins.dokka)
}

kotlin {
    jvmToolchain(AirinMetadata.JVM_TOOLCHAIN_VERSION)
}

dependencies {
    api(projects.airinCore)
    api(projects.airinGradlePlugin)

    api(libs.pendant.starlark)
    api(libs.pendant.library.bazel)
    compileOnly(libs.gradlePlugin.android.api)
    compileOnly(libs.gradlePlugin.android)
}

gradlePlugin {
    plugins {
        val airin by registering {
            id = "io.morfly.airin.android"
            implementationClass = "io.morfly.airin.plugin.AirinAndroidGradlePlugin"
        }
    }
}
