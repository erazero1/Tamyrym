plugins {
    id("custom-android-library")
}

dependencies {
    implementation(platform(getLibrary("koin.bom")))
    implementation(getBundle("bundles.koin"))
}