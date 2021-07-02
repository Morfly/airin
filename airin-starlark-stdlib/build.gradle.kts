plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
    id("org.jetbrains.dokka")
    `maven-publish-config`
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

tasks.withType<Jar>().configureEach {
    from("$buildDir/generated/ksp/main/kotlin")
}

dependencies {
    implementation(project(":airin-starlark"))
    ksp(project(":airin-starlark-libgen"))

    testImplementation(deps.bundles.kotest)
}