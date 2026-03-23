plugins {
    id("base-data")
}

android {
    namespace = "erazero1.auth.data"
}

dependencies {
    implementation(project(":feature:auth:domain"))
}