import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

fun Project.getLibrary(library: String): Provider<MinimalExternalModuleDependency> {
    return extensions.getByType<VersionCatalogsExtension>().named("libs").findLibrary(library).get()
}

fun Project.getBundle(bundle: String): Provider<ExternalModuleDependencyBundle> {
    return extensions.getByType<VersionCatalogsExtension>()
        .named("libs")
        .findBundle(bundle)
        .get()
}

internal fun Project.implementation(dependency: Any) {
    dependencies.add("implementation", dependency)
}

internal fun Project.debugImplementation(dependency: Any) {
    dependencies.add("debugImplementation", dependency)
}

internal fun Project.androidTestImplementation(dependency: Any) {
    dependencies.add("androidTestImplementation", dependency)
}

internal fun Project.testImplementation(dependency: Any) {
    dependencies.add("testImplementation", dependency)
}