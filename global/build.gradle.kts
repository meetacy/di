plugins {
    id("kmp-library-convention")
}

version = libs.versions.mdi.get()

dependencies {
    commonMainImplementation(projects.core)
}
