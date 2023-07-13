plugins {
    id("android-library-convention")
}

version = libs.versions.mdi.get()

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    api(projects.android)
    implementation(libs.composeRuntime)
}
