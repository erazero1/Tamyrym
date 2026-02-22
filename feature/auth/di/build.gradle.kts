plugins {
    id("base-di")
}

android {
    namespace = "erazero1.auth.di"
}

dependencies {
    implementation(project(":feature:auth:data"))
    implementation(project(":feature:auth:domain"))
    implementation(project(":feature:auth:ui"))
}