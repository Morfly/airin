import io.morfly.airin.buildtools.AirinConventionPlugin
import io.morfly.airin.buildtools.CommonExtension
import io.morfly.airin.buildtools.android
import io.morfly.airin.buildtools.implementation
import io.morfly.airin.buildtools.kotlinOptions
import io.morfly.airin.buildtools.sampleLibs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class SampleAndroidCommonPlugin : AirinConventionPlugin({
    with(pluginManager) {
        apply(sampleLibs.plugins.kotlin.android.get().pluginId)
    }

    android<CommonExtension> {
        compileSdk = SampleMetadata.COMPILE_SDK

        defaultConfig {
            minSdk = SampleMetadata.MIN_SDK

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = SampleMetadata.JAVA_VERSION
            targetCompatibility = SampleMetadata.JAVA_VERSION
        }
        kotlinOptions {
            jvmTarget = SampleMetadata.JAVA_VERSION.toString()
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = SampleMetadata.JAVA_VERSION.toString()
        }
    }

    dependencies {
        implementation(platform(sampleLibs.kotlin.bom))
    }
})