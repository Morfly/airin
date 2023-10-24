plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish-config`
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":airin-starlark"))

    testImplementation(deps.bundles.kotest)
}