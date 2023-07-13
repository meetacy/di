plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

dependencies {
    api(libs.kotlinPlugin)
    api(libs.androidPlugin)
}
