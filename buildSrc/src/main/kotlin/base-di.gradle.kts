import gradle.kotlin.dsl.accessors._4c73ff80cb77eaba5334f3f84dff5492.implementation

plugins {
    id("custom-android-library")
}

dependencies {
    implementation(platform(getLibrary("koin.bom")))
    implementation(getBundle("koin"))
    implementation(getBundle("network"))
}