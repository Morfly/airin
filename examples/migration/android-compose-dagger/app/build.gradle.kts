plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "org.morfly.airin.sample"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        named("debug") {
            storeFile = file("../debug.keystore")
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeVersion"] as String
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data:impl"))
    implementation(project(":image-list:impl"))
    implementation(project(":profile:impl"))

    // ===== android =====

    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

    // ===== compose =====

    val composeVersion: String by rootProject.extra
    val composeActivityVersion: String by rootProject.extra
    val composeNavigationVersion: String by rootProject.extra
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.activity:activity-compose:$composeActivityVersion")
    implementation("androidx.navigation:navigation-compose:$composeNavigationVersion")

    // ===== dagger =====

    val daggerVersion: String by rootProject.extra
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    // ===== tests =====

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}