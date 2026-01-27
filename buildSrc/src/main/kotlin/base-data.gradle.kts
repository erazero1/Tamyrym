plugins {
    id("custom-android-library")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(getLibrary("kotlinx.serialization.core"))
    implementation(getBundle("bundles.network"))
    implementation(getBundle("bundles.persistence"))
}