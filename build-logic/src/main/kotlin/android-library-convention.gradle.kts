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

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("android") {
            groupId = "app.meetacy.di"
            artifactId = "android"
            afterEvaluate {
                version = versionFromProperties()
                from(components["release"])
            }
        }
    }
}

kotlin {
    explicitApi()
}
