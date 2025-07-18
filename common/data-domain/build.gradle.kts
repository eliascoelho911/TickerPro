import com.eliascoelho911.tickerpro.plugin.CommonKotlinPlugin

plugins {
    id(libs.plugins.kotlinSerialization.get().pluginId)
    id(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId)
}

apply<CommonKotlinPlugin>()

dependencies {
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.coroutines.core)
}