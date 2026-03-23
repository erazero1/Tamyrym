import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
    id("custom-android-library")
    id("com.google.devtools.ksp")
}

val baseUrl: String = gradleLocalProperties(rootDir, providers)
    .getProperty("base_url", "")

android {
    namespace = ("core.data")

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    ksp(libs.room.compiler)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.bundles.network)
    implementation(libs.bundles.persistence)
}