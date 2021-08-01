// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion by extra("1.5.10")
    val composeVersion by extra("1.0.0")
    val composeActivityVersion by extra("1.3.0")
    val composeNavigationVersion by extra("2.4.0-alpha05")
    val composeViewModelVersion by extra("1.0.0-alpha05")
    val composePagingVersion by extra("1.0.0-alpha12")
    val composeCoilVersion by extra("0.15.0")
    val daggerVersion by extra("2.36")
    val pagingVersion by extra("3.0.0")
    val retrofitVersion by extra("2.9.0")
    val moshiVersion by extra("1.12.0")
    val roomVersion by extra("2.3.0")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha02")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

plugins {
    id("org.morfly.airin")
}

airin {
    templates {

        register<Workspace>()
        register<RootBuild>()
        register<ToolsBuild>()
        register<ThirdPartyBuild>()
        register<AndroidModuleBuild>()
    }

    artifacts {
        ignored = listOf(
            "com.google.dagger:dagger",
            "com.google.dagger:dagger-compiler"
        )
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}