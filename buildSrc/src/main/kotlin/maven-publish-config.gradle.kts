/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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