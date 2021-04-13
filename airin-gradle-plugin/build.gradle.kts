plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
}

gradlePlugin {
    plugins {
        create("airin") {
            id = "com.morfly.airin"
            implementationClass = "com.morfly.airin.AirinGradlePlugin"
        }
    }
}

dependencies {
    implementation(project(":airin-starlark"))
    implementation(kotlin("stdlib"))

    val kotestVersion: String by rootProject.extra
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}