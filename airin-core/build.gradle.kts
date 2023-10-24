import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.airin.metadata)
    alias(libs.plugins.airin.maven.publish)
    alias(libs.plugins.dokka)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}

kotlin {
    jvm {
        jvmToolchain(AirinMetadata.JVM_TOOLCHAIN_VERSION)
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.pendant.starlark)
                implementation(libs.pendant.library.bazel)
            }
        }
        val jvmTest by getting
    }
}
