import com.android.build.api.dsl.ApplicationExtension
import io.morfly.airin.buildtools.AirinConventionPlugin
import io.morfly.airin.buildtools.android
import io.morfly.airin.buildtools.sampleLibs

class SampleAndroidApplicationPlugin : AirinConventionPlugin({
    with(pluginManager) {
        apply(sampleLibs.plugins.android.application.get().pluginId)
        apply(sampleLibs.plugins.sample.android.common.get().pluginId)
    }

    android<ApplicationExtension> {
        defaultConfig {
            targetSdk = SampleMetadata.TARGET_SDK

            versionCode = 1
            versionName = "1.0"

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        signingConfigs {
            create("release") {
                storeFile = file("release-keystore.jks")
                storePassword = "123456"
                keyAlias = "release"
                keyPassword = "123456"
            }
        }

        buildTypes {
            getByName("release") {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
})