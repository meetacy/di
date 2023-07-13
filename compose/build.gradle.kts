plugins {
    id("android-library-convention")
}

version = libs.versions.mdi.get()

android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    api(projects.android)
    implementation(libs.composeRuntime)
}
