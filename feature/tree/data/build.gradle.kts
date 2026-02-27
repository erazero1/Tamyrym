plugins {
    id("base-data")
}

android {
    namespace = "erazero1.tree.data"
}

dependencies {
    implementation(project(":feature:tree:domain"))
}