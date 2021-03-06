plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish-config`
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":airin-gradle"))

    compileOnly(deps.agp)
}