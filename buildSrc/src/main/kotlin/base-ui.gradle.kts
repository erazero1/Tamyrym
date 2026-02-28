plugins {
    id("custom-android-library")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization")
}

android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    api(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(getBundle("android"))
    implementation(platform(getLibrary("androidx.compose.bom")))
    implementation(platform(getLibrary("koin.bom")))
    implementation(platform(getLibrary("firebase.bom")))
    implementation(getBundle("compose"))
    implementation(getBundle("analytics"))
    implementation(getBundle("ui.utils"))
}