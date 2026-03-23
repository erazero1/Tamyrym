plugins {
    id("base-data")
}

android {
    namespace = "erazero1.auth.data"
}

dependencies {
    implementation(project(":feature:auth:domain"))
    implementation(libs.play.services.auth)
    implementation(libs.identity.google.id)
}