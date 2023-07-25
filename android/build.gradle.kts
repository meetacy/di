plugins {
    id("android-library-convention")
}

version = libs.versions.mdi.get()

android {
    namespace = "app.meetacy.di.android"
}

dependencies {
    api(projects.core)
    implementation(projects.global)
}
