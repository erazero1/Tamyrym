plugins {
    id("base-di")
}

android {
    namespace = "eraezero1.profile.di"
}

dependencies {
    implementation(project(":feature:profile:data"))
    implementation(project(":feature:profile:domain"))
    implementation(project(":feature:profile:ui"))
}