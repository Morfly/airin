@file:Suppress("HasPlatformType")

import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
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

tasks {
    register<Jar>("javadocJar") {
        dependsOn(named("dokkaHtml"))
        archiveClassifier.set("javadoc")
        from("$buildDir/dokka/html")
    }
    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].java.srcDirs)
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenAirinStarlark") {
            artifactId = "airin-starlark"
            artifact(tasks.getByName("javadocJar"))
            artifact(tasks.getByName("sourcesJar"))

            pom {
                name.set("Airin Starlark")
                description.set("A Kotlin tool for migrating Gradle projects to Bazel. It provides a declarative and type-safe Starlark template engine for generating Bazel build scripts.")
                url.set("https://github.com/Morfly/airin")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("morfly")
                        name.set("Pavlo Stavytskyi")
                        email.set("pavlo.stavytskyi@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Morfly/airin.git")
                    developerConnection.set("scm:git:ssh://github.com/Morfly/airin.git")
                    url.set("https://github.com/Morfly/airin")
                }
            }

            from(components["kotlin"])
        }

        repositories {
            maven {
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenAirinStarlark"])
}