package com.eliascoelho911.tickerpro.plugin

import com.eliascoelho911.tickerpro.extension.android
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class SetupJavaVersion : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            tasks.withType(KotlinCompile::class.java) {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_19.toString()
                }
            }

            runCatching {
                android.compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_19
                    targetCompatibility = JavaVersion.VERSION_19
                }
            }.onFailure {
                extensions.getByType(JavaPluginExtension::class.java).apply {
                    sourceCompatibility = JavaVersion.VERSION_19
                    targetCompatibility = JavaVersion.VERSION_19
                }
            }
        }
    }
}