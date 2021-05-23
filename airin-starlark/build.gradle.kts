@file:Suppress("HasPlatformType")

plugins {
    kotlin("jvm")
}

val functionalTest by sourceSets.creating

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    val kotestVersion: String by rootProject.extra
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}