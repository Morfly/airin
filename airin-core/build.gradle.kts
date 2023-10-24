import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}

kotlin {
    jvm {
//        jvmToolchain(AirinMed.JVM_TOOLCHAIN_VERSION)
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
