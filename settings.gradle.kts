enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "mdi"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

includeBuild("build-logic")
