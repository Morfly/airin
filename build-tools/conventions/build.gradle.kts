plugins {
    `kotlin-dsl`
}

dependencies {
    // Makes 'libs' version catalog visible and type-safe for precompiled plugins.
    // https://github.com/gradle/gradle/issues/15383#issuecomment-1245546796
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(files(sampleLibs.javaClass.superclass.protectionDomain.codeSource.location))

    compileOnly(libs.gradlePlugin.kotlin)
    compileOnly(libs.gradlePlugin.mavenPublish)
    compileOnly(sampleLibs.gradlePlugin.android.api)
}

gradlePlugin {
    plugins {
        val airinMetadata by registering {
            id = "airin.metadata"
            implementationClass = "AirinMetadataPlugin"
        }
        val mavenPublish by registering {
            id = "airin.maven.publish"
            implementationClass = "AirinMavenPublishPlugin"
        }

        val sampleMetadata by registering {
            id = "sample.metadata"
            implementationClass = "SampleMetadataPlugin"
        }
        val sampleAndroidCommon by registering {
            id = "sample.android.common"
            implementationClass = "SampleAndroidCommonPlugin"
        }
        val sampleAndroidLibrary by registering {
            id = "sample.android.library"
            implementationClass = "SampleAndroidLibraryPlugin"
        }
        val sampleAndroidApplication by registering {
            id = "sample.android.application"
            implementationClass = "SampleAndroidApplicationPlugin"
        }
        val sampleAndroidCompose by registering {
            id = "sample.android.compose"
            implementationClass = "SampleAndroidComposePlugin"
        }
    }
}
