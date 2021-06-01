@file:Suppress("HasPlatformType")

import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    id("org.jetbrains.dokka")
    kotlin("jvm")
}

sourceSets {
    create("functionalTest") {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/functionalTest/kotlin")
            resources.srcDir("src/functionalTest/resources")
            compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
}

task<Test>("functionalTest") {
    description = "Runs the functional tests"
    group = "verification"
    testClassesDirs = sourceSets["functionalTest"].output.classesDirs
    classpath = sourceSets["functionalTest"].runtimeClasspath
    mustRunAfter(tasks["test"])
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib"))

    val kotestVersion: String by rootProject.extra
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}