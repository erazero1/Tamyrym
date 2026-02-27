plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
}
android {
    compileSdk = Const.TARGET_SDK

    defaultConfig {
        minSdk = Const.MIN_SDK
        testInstrumentationRunner = Const.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Const.SOURCE_COMPATIBILITY
        targetCompatibility = Const.TARGET_COMPATIBILITY
    }
    kotlin {
        compilerOptions {
            jvmTarget = Const.JVM_TARGET
        }
    }
}

dependencies {
    api(project(":core:utils"))
}