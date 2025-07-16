import com.eliascoelho911.tickerpro.plugin.CommonAndroidComposePlugin

plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose.compiler)
}

apply<CommonAndroidComposePlugin>()

android {
    namespace = "com.eliascoelho911.tickerpro.core"
}

// TODO: Migrate using https://github.com/MAshhal/ComposeCompilerConventionPlugin/blob/main/build-logic/convention/build.gradle.kts
composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.koin.android)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.compose.runtime)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.navigation.compose)
    implementation(libs.kotlin.serialization.json)
    implementation(project(":logs"))
}