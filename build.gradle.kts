import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    kotlin("jvm") version "1.4.21" apply false
    kotlin("jvm") version "1.5.0" apply false
}

subprojects {
    group = "com.morfly.airin"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

//    tasks.withType<KotlinCompile> {
//        kotlinOptions.jvmTarget = "1.8"
//    }
}

val kotlinCoroutinesVersion by extra("1.4.3")
val kotestVersion by extra("4.4.3")