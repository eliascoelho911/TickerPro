plugins {
    id(libs.plugins.com.android.application.get().pluginId) apply false
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId) apply false
    id(libs.plugins.androidLibrary.get().pluginId) apply false
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinSerialization) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.navigation.safeargs.plugin)
        classpath(libs.androidTools)
    }
}