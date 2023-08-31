package app.meetacy.di.global.annotation

@RequiresOptIn(
    message = "Be careful when using such API. " +
            "First of all it's immutable (and it will be), so you initialize it's value only once. " +
            "That already means, that you can't use global DI if you plan testing things. " +
            "It was made primarily for Android where global singleton is the only way to transfer dependencies " +
            "between modules",
    level = RequiresOptIn.Level.WARNING
)
public annotation class GlobalApi
