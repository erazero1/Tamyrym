plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}
android {
    compileSdk = Const.TARGET_SDK
    defaultConfig {
        applicationId = Const.APPLICATION_ID
        minSdk = Const.MIN_SDK
        targetSdk = Const.TARGET_SDK
        versionCode = Const.VERSION_CODE
        versionName = Const.VERSION_NAME

        testInstrumentationRunner = Const.TEST_INSTRUMENTATION_RUNNER
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
    buildFeatures {
        compose = true
    }
}