plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(projects.airinCore)
    implementation(projects.airinGradlePlugin)

    implementation(libs.pendant.starlark)
    implementation(libs.pendant.library.bazel)
}

gradlePlugin {
    plugins {
        val airin by registering {
            id = "io.morfly.airin.android"
            implementationClass = "io.morfly.airin.plugin.AirinGradlePlugin"
        }
    }
}
