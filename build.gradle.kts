import task.CoreModuleCreatorTask
import task.FeatureModuleCreatorTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

}

tasks.register<FeatureModuleCreatorTask>("createFeatureModule") {
    featureName = project.findProperty("moduleName") as? String
}

tasks.register<CoreModuleCreatorTask>("createCoreModule") {
    moduleName = project.findProperty("moduleName") as? String
}