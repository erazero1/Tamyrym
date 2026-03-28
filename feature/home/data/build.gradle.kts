plugins {
    id("base-data")
}

android {
    namespace = "erazero1.home.data"
}

dependencies {
    implementation(project(":feature:home:domain"))
}