plugins {
    `maven-publish`
    signing
}

val sourceSets = the<SourceSetContainer>()

tasks {
    register<Jar>("javadocJar") {
        dependsOn(named("dokkaHtml"))
        archiveClassifier.set("javadoc")
        from("$buildDir/dokka/html")
    }
    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allJava.srcDirs)
    }
}


val airinMaven = "airinMaven"

publishing {
    publications {
        register<MavenPublication>(airinMaven) {
            artifactId = project.name
            artifact(tasks.getByName("javadocJar"))
            artifact(tasks.getByName("sourcesJar"))

            pom {
                name.set(airinPublications[project.name]!!.name)
                description.set(airinPublications[project.name]!!.description)
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
                // publishAirinMavenPublicationToMavenRepository
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    val sonatypeUsername: String? by project
                    val sonatypePassword: String? by project
                    username = sonatypeUsername ?: ""
                    password = sonatypePassword ?: ""
                }
            }
            maven {
                // publishAirinMavenPublicationToLocalRepository
                name = "local"
                url = uri("$buildDir/repo")
            }
        }
    }
}

signing {
    sign(publishing.publications[airinMaven])
//    useGpgCmd()
}