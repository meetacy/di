plugins {
    id("android-library-convention")
}

version = libs.versions.mdi.get()

android {
    namespace = "app.meetacy.di.android.compose"

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
    implementation(libs.lifecycleComposeViewmodel)
}
