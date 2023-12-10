import com.vanniktech.maven.publish.SonatypeHost
import io.morfly.airin.buildtools.AirinConventionPlugin
import io.morfly.airin.buildtools.libs
import io.morfly.airin.buildtools.mavenPublishing

class AirinMavenPublishPlugin : AirinConventionPlugin({
    with(pluginManager) {
        apply(libs.plugins.vanniktech.maven.publish.get().pluginId)
        apply(libs.plugins.dokka.get().pluginId)
    }

    mavenPublishing {
        val version: String by properties
        coordinates(
            groupId = AirinMetadata.ARTIFACT_GROUP,
            artifactId = project.name,
            version = version
        )

        pom {
            name.set("Airin")
            description.set("Tool for automated migration of Gradle projects to Bazel.")
            inceptionYear.set("2023")
            url.set("https://github.com/Morfly/airin")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("Morfly")
                    name.set("Pavlo Stavytskyi")
                    url.set("https://github.com/Morfly")
                }
            }
            scm {
                url.set("https://github.com/Morfly/airin")
                connection.set("scm:git:git://github.com/Morfly/airin.git")
                developerConnection.set("scm:git:ssh://git@github.com/Morfly/airin.git")
            }
        }

        publishToMavenCentral(SonatypeHost.S01)
        signAllPublications()
    }
})

