plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish-config`
}

dependencies {
    implementation(project(":airin-starlark"))

    implementation(kotlin("stdlib"))
    implementation(deps.ksp)
}