import com.eliascoelho911.ebookreader.plugin.AndroidFeaturePlugin

plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    alias(libs.plugins.compose.compiler)
}

apply<AndroidFeaturePlugin>()

android {
    namespace = "com.eliascoelho911.ebookreader.home"
}

// TODO: Migrate using https://github.com/MAshhal/ComposeCompilerConventionPlugin/blob/main/build-logic/convention/build.gradle.kts
composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
    implementation(libs.material.icons)
}