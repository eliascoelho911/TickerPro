package com.eliascoelho911.ebookreader.plugin

import com.eliascoelho911.ebookreader.ProjectConfig
import com.eliascoelho911.ebookreader.extension.android
import com.eliascoelho911.ebookreader.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies

class CommonAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            applyPlugins()
            androidConfig()
            setupTestTask()
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply(SetupTestPlugin::class.java)
        plugins.apply(SetupJavaVersion::class.java)
    }

    private fun Project.androidConfig() {
        android.run {
            compileSdkVersion(ProjectConfig.COMPILE_SDK)

            defaultConfig {
                minSdkPreview = ProjectConfig.MIN_SDK.toString()
                targetSdkPreview = ProjectConfig.TARGET_SDK.toString()

                vectorDrawables.useSupportLibrary = true

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            testOptions {
                unitTests {
                    isIncludeAndroidResources = true
                }
                packagingOptions {
                    jniLibs {
                        useLegacyPackaging = true
                    }
                }
                animationsDisabled = true
            }

//            packagingOptions {
//                resources {
//                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
//                }
//            }
        }

        dependencies {
            "testImplementation"(libs.findLibrary("robolectric").get())
            "testImplementation"(libs.findLibrary("androidx.test.ext.junit").get())
            "testImplementation"(libs.findLibrary("turbine").get())
        }
    }

    private fun Project.setupTestTask() = tasks.withType(Test::class.java) {
        systemProperties["robolectric.logging"] = "stdout"
    }
}