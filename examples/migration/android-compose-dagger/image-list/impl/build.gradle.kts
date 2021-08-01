plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeVersion"] as String
    }
}

dependencies {
    api(project(":image-list:api"))
    implementation(project(":core"))
    implementation(project(":data:api"))
    implementation(project(":profile:api"))

    // ===== android =====

    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")

    // ===== compose =====

    val composeVersion: String by rootProject.extra
    val composeActivityVersion: String by rootProject.extra
    val composeNavigationVersion: String by rootProject.extra
    val composeViewModelVersion: String by rootProject.extra
    val composeCoilVersion: String by rootProject.extra
    val composePagingVersion: String by rootProject.extra
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.activity:activity-compose:$composeActivityVersion")
    implementation("androidx.navigation:navigation-compose:$composeNavigationVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$composeViewModelVersion")
    implementation("com.google.accompanist:accompanist-coil:$composeCoilVersion")
    implementation("androidx.paging:paging-compose:$composePagingVersion")

    // ===== dagger =====
    
    val daggerVersion: String by rootProject.extra
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    // ===== paging =====

    val pagingVersion: String by rootProject.extra
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    // ===== tests =====

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}