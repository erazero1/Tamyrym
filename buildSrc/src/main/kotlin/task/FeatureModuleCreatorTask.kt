package task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

abstract class FeatureModuleCreatorTask : DefaultTask() {
    private val defaultFeatureName = "new-feature"

    init {
        group = "create module"
        description = """
            Creates a module inside feature directory.
            Sets module name $defaultFeatureName if no feature name is provided.
            E.g.
            ./gradlew createFeatureModule -PmoduleName=auth
            ./gradlew createFeatureModule
        """.trimIndent()
    }

    @Input
    @Optional
    var featureName: String? = null

    @TaskAction
    fun createModule() {
        val featureName = this.featureName ?: defaultFeatureName
        val featureDir = project.rootDir.resolve("feature/$featureName")
        val modules = listOf("data", "domain", "ui", "di")
        val settingsFile = project.rootDir.resolve("settings.gradle.kts")

        featureDir.mkdirs()

        modules.forEach { module ->
            val moduleDir = featureDir.resolve(module)
            moduleDir.mkdirs()

            val srcDir = moduleDir.resolve("src/main/kotlin/feature/$featureName/$module")
            srcDir.mkdirs()
            val gitignoreFile = moduleDir.resolve(".gitignore")
            val gitignoreContent = """
                /build
            """.trimIndent()
            gitignoreFile.writeText(gitignoreContent)

            val buildFile = moduleDir.resolve("build.gradle.kts")
            val namespace = "erazero1.$featureName.$module"

            val buildScript = when (module) {
                "data" -> """
                    plugins {
                        id("base-data")
                    }
                    
                    android {
                        namespace = "$namespace"
                    }
                    
                    dependencies {
                        implementation(project(":feature:$featureName:domain"))
                    }
                """.trimIndent()

                "domain" -> """
                    plugins {
                        id("base-domain")
                    }
                """.trimIndent()

                "ui" -> """
                    plugins {
                        id("base-ui")
                    }
                    
                    android {
                        namespace = "$namespace"
                    }
                    
                    dependencies {
                        implementation(project(":feature:$featureName:domain"))
                    }
                """.trimIndent()

                "di" -> """
                    plugins {
                        id("base-di")
                    }
                    
                    android {
                        namespace = "eraezero1.$featureName.di"
                    }
                    
                    dependencies {
                        implementation(project(":feature:$featureName:data"))
                        implementation(project(":feature:$featureName:domain"))
                        implementation(project(":feature:$featureName:ui"))
                    }
                """.trimIndent()

                else -> ""
            }
            buildFile.writeText(buildScript)
        }

        val includeStatements =
            modules.joinToString("\n") { "include(\":feature:$featureName:$it\")" }
        settingsFile.appendText("\n$includeStatements")
    }
}