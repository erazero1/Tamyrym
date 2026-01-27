plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}
java {
    sourceCompatibility = Const.SOURCE_COMPATIBILITY
    targetCompatibility = Const.TARGET_COMPATIBILITY
}
kotlin {
    compilerOptions {
        jvmTarget = Const.JVM_TARGET
    }
}