plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

//tasks.withType<Jar>().configureEach {
//    from("$buildDir/generated/ksp/main/java")
//    from("$buildDir/generated/ksp/main/kotlin")
//}

//sourceSets {
//    main {
//        java.srcDir(file("$buildDir/generated/ksp/main/kotlin"))
//    }
//}

kotlin {
    sourceSets {
        main {
            kotlin.srcDirs(
                file("$buildDir/generated/ksp/main/kotlin"),
                file("$buildDir/generated/ksp/main/java")
            )
        }
    }
}

//idea {
//    module {
//        generatedSourceDirs = setOf(
//            file("$buildDir/generated/ksp/main/java"),
//            file("$buildDir/generated/ksp/main/kotlin")
//        )
//    }
//}

dependencies {
    implementation(project(":airin-starlark"))
    ksp(project(":airin-library-generator"))

    testImplementation(deps.bundles.kotest)
}