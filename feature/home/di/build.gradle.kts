plugins {
    id("base-di")
}

android {
    namespace = "eraezero1.home.di"
}

dependencies {
    implementation(project(":feature:home:data"))
    implementation(project(":feature:home:domain"))
    implementation(project(":feature:auth:domain"))
    implementation(project(":feature:home:ui"))
    implementation(project(":feature:tree:domain"))
}