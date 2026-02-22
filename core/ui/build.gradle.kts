plugins {
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("plugin.serialization")
    id("custom-android-library")
}

android {
    namespace = ("core.presentation")
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.bundles.android)
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.ui.utils)
}