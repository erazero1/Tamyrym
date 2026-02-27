plugins {
    id("base-ui")
}

android {
    namespace = "erazero1.profile.ui"
}

dependencies {
    implementation(project(":feature:profile:domain"))
    api(project(":feature:auth:domain"))
}