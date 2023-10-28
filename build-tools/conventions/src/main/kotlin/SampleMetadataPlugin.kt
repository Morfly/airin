import io.morfly.airin.buildtools.AirinConventionPlugin
import org.gradle.api.JavaVersion

class SampleMetadataPlugin : AirinConventionPlugin({})

object SampleMetadata {
    const val MIN_SDK = 24
    const val TARGET_SDK = 34
    const val COMPILE_SDK = 34

    val JAVA_VERSION = JavaVersion.VERSION_11
}