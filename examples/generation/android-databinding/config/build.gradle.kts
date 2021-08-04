import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.5.10"
}

group = "org.morfly.airin"
version = "1.0.0"

application {
    mainClass.set("org.morfly.airin.sample.MainKt")
}

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib"))

    val airinVersion = "0.4.0"
    implementation("org.morfly.airin:airin-starlark:$airinVersion")
    implementation("org.morfly.airin:airin-starlark-stdlib:$airinVersion")
}
