plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    //module ksp para room
    id("com.google.devtools.ksp")

    //fireb
        // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    
    // Kotlin Serialization for Data Connect
    kotlin("plugin.serialization") version "2.2.10"
}

android {
    namespace = "com.gabrieldev.appcomplect"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.gabrieldev.appcomplect"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //adpendencias necesarias
    // dependencias Nearby Connections
    implementation("com.google.android.gms:play-services-nearby:19.3.0")
        // GSON para cmpartir datos de Nearby
    implementation("com.google.code.gson:gson:2.10.1")

    //dependencias room
    val room_version = "2.8.4"

    implementation("androidx.room:room-runtime:$room_version")

        // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
        // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:$room_version")

        // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    //fireb
        // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:34.11.0"))

        // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore")
        // para corrutinas con Firebase
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Firebase Data Connect Core SDK
    implementation("com.google.firebase:firebase-dataconnect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.3")

    // If you're using Firebase Authentication with Data Connect
    implementation("com.google.firebase:firebase-auth")
    
    // ViewModel setup for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    
    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}