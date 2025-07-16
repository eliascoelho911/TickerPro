package com.eliascoelho911.tickerpro.plugin

import com.eliascoelho911.tickerpro.extension.android
import com.eliascoelho911.tickerpro.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class CommonAndroidComposePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            applyPlugins()
            androidConfig()
            setupDependencies()
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply(CommonAndroidPlugin::class.java)
    }

    private fun Project.androidConfig() {
        android.run {
            buildFeatures.compose = true

            tasks.withType(KotlinCompile::class.java) {
                kotlinOptions {
                    freeCompilerArgs =
                        listOf("-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api")
                }
            }
        }
    }

    private fun Project.setupDependencies() {
        dependencies {
            "implementation"(platform(libs.findLibrary("compose.bom").get()))
        }
    }
}