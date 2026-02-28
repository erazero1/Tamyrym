import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Const {
    const val APPLICATION_ID = "com.erazero1.tamyrym"
    const val MIN_SDK = 26
    const val TARGET_SDK = 36
    const val COMPILE_SDK = 36
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    val SOURCE_COMPATIBILITY = JavaVersion.VERSION_11
    val TARGET_COMPATIBILITY = JavaVersion.VERSION_11
    val JVM_TARGET = JvmTarget.JVM_11
}