package app.meetacy.di.android.annotation

@RequiresOptIn(
    message = "Be careful when using such API. " +
            "First of all it's immutable (and it will be), so you initialize it's value only once. " +
            "That already means, that you can't use global DI if you plan testing things. " +
            "It was made primarily meant for integrations between modules where there is no " +
            "another option, but to use a Global singleton",
    level = RequiresOptIn.Level.WARNING
)
annotation class AndroidGlobalApi()
