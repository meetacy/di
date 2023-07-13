enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "mdi"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

includeBuild("build-logic")

include(
    "core",
    "android",
    "compose"
)
