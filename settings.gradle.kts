pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://api.xposed.info/repository") }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "ZuanZuanLeHook"
include(":app")
