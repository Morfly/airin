plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.morfly.airin.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.morfly.airin.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = sampleLibs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(sampleLibs.androidx.core)
    implementation(sampleLibs.androidx.lifecycle.runtime)
    implementation(platform(sampleLibs.compose.bom))
    implementation(sampleLibs.compose.ui)
    implementation(sampleLibs.compose.ui.graphics)
    implementation(sampleLibs.compose.ui.tooling.preview)
    implementation(sampleLibs.compose.material3)
    implementation(sampleLibs.compose.activity)

    debugImplementation(sampleLibs.compose.ui.tooling)

    testImplementation(sampleLibs.junit)
}