plugins {
    id("android-library-convention")
}

version = libs.versions.mdi.get()

dependencies {
    api(projects.core)
}
