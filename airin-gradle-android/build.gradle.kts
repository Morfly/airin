plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish-config`
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":airin-gradle"))

    implementation("com.android.tools.build:gradle:4.1.0")
}