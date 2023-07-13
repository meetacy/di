plugins {
    id("com.android.library")
    kotlin("android")
    id("publication-convention")
}

android {
    namespace = "app.meetacy.di.android"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}

kotlin {
    explicitApi()
}
