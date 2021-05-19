plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
}

gradlePlugin {
    plugins {
        create("airin") {
            id = "com.morfly.airin"
            implementationClass = "com.morfly.airin.plugin.AirinGradlePlugin"
        }
    }
}

dependencies {
    val kotlinCoroutinesVersion: String by rootProject.extra
    val kotestVersion: String by rootProject.extra

    implementation(project(":airin-starlark"))
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("org.morfly.bazelgen:generator:0.0.1")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}