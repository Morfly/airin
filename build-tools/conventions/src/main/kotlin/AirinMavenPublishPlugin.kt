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
            description.set("TODO")
            inceptionYear.set("2023")
            url.set("https://github.com/Morfly/airin")
            licenses {
                license {
                    name.set("The MIT License")
                    url.set("https://opensource.org/license/mit/")
                    distribution.set("https://opensource.org/license/mit/")
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

