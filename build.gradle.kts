plugins {
    kotlin("jvm") version "1.5.0" apply false
}

subprojects {
    group = "com.morfly.airin"
    version = "0.0.1"

    repositories {
        mavenCentral()
        google()
    }
}

val kotlinCoroutinesVersion by extra("1.4.3")
val kotestVersion by extra("4.4.3")