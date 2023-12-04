plugins {
    `kotlin-dsl`
    alias(libs.plugins.airin.maven.publish)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.publish)
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
    website = "https://github.com/Morfly/airin"
    vcsUrl = "https://github.com/Morfly/airin"

    plugins {
        val airin by registering {
            id = "io.morfly.airin.android"
            displayName = "airin"
            description = "A plugin for automated the migration from Gradle to Bazel."
            tags = listOf("android", "bazel", "kotlin", "starlark", "migration")
            implementationClass = "io.morfly.airin.plugin.AirinAndroidGradlePlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
    }
}
