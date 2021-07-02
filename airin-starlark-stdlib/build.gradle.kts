plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDirs(
                file("$buildDir/generated/ksp/main/kotlin"),
            )
        }
    }
}

dependencies {
    implementation(project(":airin-starlark"))
    ksp(project(":airin-library-generator"))

    testImplementation(deps.bundles.kotest)
}