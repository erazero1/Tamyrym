plugins {
    id("base-ui")
}

android {
    namespace = "com.erazero1.splash"
}

dependencies {
    implementation(libs.lottie.compose)
    implementation(project(":feature:auth:domain"))
}