package com.eliascoelho911.ebookreader.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CommonKotlinPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins()
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply(SetupTestPlugin::class.java)
        plugins.apply(SetupJavaVersion::class.java)
    }
}