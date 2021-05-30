plugins {
    `java-library`
    kotlin("jvm")
    id("com.morfly.airin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    val kotlinVersion: String by rootProject.extra
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
}

airin {
    templates {
        register<AndroidWorkspace>()
        register<KotlinLibrary>()
        register<AndroidApplication>()
    }

    artifacts {
        ignored = listOf(
            "org.jetbrains.kotlin:kotlin-stdlib"
        )
    }
}