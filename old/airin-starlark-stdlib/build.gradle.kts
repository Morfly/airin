import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
    id("org.jetbrains.dokka")
    `maven-publish-config`
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        languageVersion = "1.7"
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xcontext-receivers"
        )
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":airin-starlark"))
    ksp(project(":airin-starlark-libgen"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    testImplementation(deps.bundles.kotest)
}