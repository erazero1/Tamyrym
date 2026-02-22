import org.gradle.kotlin.dsl.dependencies

plugins {
    id("custom-android-library")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":core:data"))
    implementation(project(":core:domain"))
    ksp(getLibrary("room.compiler"))
    implementation(getLibrary("kotlinx.serialization.core"))
    implementation(getBundle("network"))
    implementation(getBundle("persistence"))
}