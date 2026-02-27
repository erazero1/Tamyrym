plugins {
    id("base-di")
}

android {
    namespace = "eraezero1.tree.di"
}

dependencies {
    implementation(project(":feature:tree:data"))
    implementation(project(":feature:tree:domain"))
    implementation(project(":feature:tree:ui"))
}