package com.eliascoelho911.tickerpro.plugin

import com.eliascoelho911.tickerpro.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CommonKotlinPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins()

            dependencies {
                "implementation"(libs.findLibrary("kotlin-stdlib").get())
            }
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply(SetupTestPlugin::class.java)
        plugins.apply(SetupJavaVersion::class.java)
    }
}