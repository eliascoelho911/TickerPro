package com.eliascoelho911.ebookreader.plugin

import com.eliascoelho911.ebookreader.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins()
            setupDependencies()
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply(CommonAndroidComposePlugin::class.java)
        plugins.apply(libs.findPlugin("kotlinSerialization").get().get().pluginId)
    }

    private fun Project.setupDependencies() {
        dependencies {
            "implementation"(libs.findLibrary("core.ktx").get())
            "implementation"(libs.findLibrary("lifecycle.runtime.ktx").get())
            "implementation"(libs.findLibrary("lifecycle.runtime.compose").get())
            "implementation"(libs.findLibrary("kotlin.coroutines.core").get())
            "implementation"(libs.findLibrary("kotlin.collections.immutable").get())
            "implementation"(libs.findLibrary("androidx.core").get())
            "implementation"(libs.findLibrary("appcompat").get())
            "implementation"(libs.findLibrary("koin.compose").get())
            "implementation"(libs.findLibrary("navigation.compose").get())
            "implementation"(libs.findLibrary("navigation.fragment.ktx").get())
            "implementation"(libs.findLibrary("navigation.ui.ktx").get())
            "implementation"(libs.findLibrary("kotlin.serialization.core").get())
            "implementation"(libs.findLibrary("kotlin.serialization.json").get())
            "implementation"(project(":common:common"))
            "implementation"(project(":common:core"))
            "implementation"(project(":logs"))
            "implementation"(project(":design-system"))
            "debugImplementation"(libs.findLibrary("ui.tooling").get())
            "debugImplementation"(libs.findLibrary("ui.test.manifest").get())
        }
    }
}