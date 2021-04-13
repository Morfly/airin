plugins {
    kotlin("jvm") version "1.4.21" apply false
}

subprojects {
    group = "com.morfly.airin"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

val kotestVersion by extra("4.4.3")