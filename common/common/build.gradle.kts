import com.eliascoelho911.tickerpro.plugin.CommonAndroidPlugin

plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
}

apply<CommonAndroidPlugin>()

android {
    namespace = "com.eliascoelho911.common"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
}