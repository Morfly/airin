plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish-config`
}

gradlePlugin {
    plugins {
        create("airin") {
            id = "org.morfly.airin"
            implementationClass = "org.morfly.airin.plugin.AirinGradlePlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":airin-starlark"))
    api(project(":airin-migration-core"))
}