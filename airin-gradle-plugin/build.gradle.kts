import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.airin.maven.publish)
    alias(libs.plugins.dokka)
}

kotlin {
    jvmToolchain(AirinMetadata.JVM_TOOLCHAIN_VERSION)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    api(projects.airinCore)

    implementation(libs.pendant.starlark)
    implementation(libs.pendant.library.bazel)
}
