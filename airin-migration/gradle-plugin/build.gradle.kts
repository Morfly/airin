plugins {
    `maven-publish`
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

publishing {

}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":airin-starlark"))
}