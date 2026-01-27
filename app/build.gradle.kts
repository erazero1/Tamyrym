plugins {
    alias(libs.plugins.custom.android.application)
}

android {
    namespace = "com.erazero1.tamyrym"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

/**
 * Get core and feature modules
 */
dependencies {
    fun File.isGradleModule() = isDirectory && File(this, "build.gradle.kts").exists()

    fun collectModule(base: File): List<String> {
        if (!base.exists()) {
            return emptyList()
        }

        return base.walkTopDown()
            .filter { it.isGradleModule() }
            .map { file ->
                val relativePath = file.relativeTo(rootDir).invariantSeparatorsPath
                ":" + relativePath.replace("/", ":")
            }.toList()
    }

    val modulePaths = listOf("core", "feature")
        .flatMap {
            collectModule(File(rootDir, it))
        }

    modulePaths.forEach { path ->
        implementation(project(path))
    }
}

dependencies {
    implementation(libs.bundles.android)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
}