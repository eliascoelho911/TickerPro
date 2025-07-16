import com.eliascoelho911.tickerpro.plugin.CommonAndroidComposePlugin

plugins {
    id(libs.plugins.com.android.application.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
    id(libs.plugins.navigation.safe.args.get().pluginId)
    id(libs.plugins.kotlinSerialization.get().pluginId)
    alias(libs.plugins.compose.compiler)
}

apply<CommonAndroidComposePlugin>()

android {
    namespace = "com.eliascoelho911.tickerpro"

    defaultConfig {
        applicationId = "com.eliascoelho911.tickerpro"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources.excludes += setOf(
            "META-INF/proguard/*",
            "META-INF/*.kotlin_module",
            "META-INF/DEPENDENCIES",
            "META-INF/AL2.0",
            "META-INF/LGPL2.1",
            "META-INF/*.properties",
            "META-INF/INDEX.LIST",
            "/*.properties"
        )
    }
}

// TODO: Migrate using https://github.com/MAshhal/ComposeCompilerConventionPlugin/blob/main/build-logic/convention/build.gradle.kts
composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.koin.compose)
    implementation(libs.navigation.compose)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(project(":common:core"))
    implementation(project(":features:home"))
    implementation(project(":design-system"))
    implementation(project(":logs"))

    testImplementation(libs.junit)
    debugImplementation(libs.ui.tooling)
}