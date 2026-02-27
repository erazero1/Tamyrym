plugins {
    id("base-data")
}

android {
    namespace = "erazero1.profile.data"
}

dependencies {
    implementation(project(":feature:profile:domain"))
}