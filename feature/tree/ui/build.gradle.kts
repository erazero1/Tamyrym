plugins {
    id("base-ui")
}

android {
    namespace = "erazero1.tree.ui"
}

dependencies {
    implementation(project(":feature:tree:domain"))
}