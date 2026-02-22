pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Tamyrym"
include(":app")

include(":core:data")
include(":core:domain")
include(":core:ui")
include(":feature:splash")

include(":feature:auth:data")
include(":feature:auth:domain")
include(":feature:auth:ui")
include(":feature:auth:di")