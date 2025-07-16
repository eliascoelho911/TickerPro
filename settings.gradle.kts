pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://github.com/psiegman/mvn-repo/raw/master/releases/")
    }
}

rootProject.name = "TickerPro"
include(":app")
include(":common:common")
include(":common:core")
include(":logs")
include(":design-system")
include(":features:home")
