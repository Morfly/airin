plugins {
    kotlin("jvm") version "1.5.0" apply false
    id("org.jetbrains.dokka") version "1.4.32" apply false
}

subprojects {
    group = "org.morfly.airin"
    version = "1.0.0-alpha01"

    repositories {
        mavenCentral()
        google()
    }
}

val kotlinCoroutinesVersion by extra("1.4.3")
val kotestVersion by extra("4.4.3")