plugins {
    id("base-ui")
}

android {
    namespace = "erazero1.auth.presentation"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":feature:auth:domain"))
}