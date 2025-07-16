package com.eliascoelho911.tickerpro.plugin

import com.eliascoelho911.tickerpro.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class SetupTestPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            setupUnitTestDependencies()
        }
    }

    private fun Project.setupUnitTestDependencies() {
        dependencies {
            "testImplementation"(libs.findLibrary("junit").get())
            "testImplementation"(libs.findLibrary("mockk").get())
            "testImplementation"(libs.findLibrary("kotlin.test").get())
            "testImplementation"(libs.findLibrary("kotlin.coroutines.test").get())
        }
    }
}