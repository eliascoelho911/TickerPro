import com.eliascoelho911.tickerpro.plugin.CommonAndroidComposePlugin

plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    alias(libs.plugins.compose.compiler)
}

apply<CommonAndroidComposePlugin>()

android {
    namespace = "com.eliascoelho911.tickerpro.ds"
}

// TODO: Migrate using https://github.com/MAshhal/ComposeCompilerConventionPlugin/blob/main/build-logic/convention/build.gradle.kts
composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}


dependencies {
    implementation(libs.material)

    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material3)
    api(libs.material.icons)
    api(libs.accompanist.placeholder.material)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)
}