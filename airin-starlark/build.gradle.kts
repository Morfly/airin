import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish-config`
}

description = "Declarative, type-safe template engine for generating Starlark code using Kotlin."

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }
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

    testImplementation(deps.bundles.kotest)
}