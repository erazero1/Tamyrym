plugins {
    id("base-ui")
}

android {
    namespace = "erazero1.home.ui"
}

dependencies {
    implementation(project(":feature:home:domain"))
    implementation(project(":feature:auth:domain"))
    implementation(project(":feature:tree:domain"))
}