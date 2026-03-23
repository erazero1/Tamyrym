import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("base-ui")
}

val googleAuthWebClientId: String = gradleLocalProperties(rootDir, providers)
    .getProperty("GOOGLE_AUTH_WEB_CLIENT_ID", "")

android {
    namespace = "erazero1.auth.presentation"
    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "GOOGLE_AUTH_WEB_CLIENT_ID", "\"$googleAuthWebClientId\"")
    }
}

dependencies {
    implementation(project(":feature:auth:domain"))
    implementation(libs.androidx.credentials)
    implementation(libs.play.services.auth)
    implementation(libs.identity.google.id)
}