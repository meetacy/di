package app.meetacy.backend.di.dependency

public data class DependencyPair<T>(
    val key: DependencyKey<T>,
    val provider: DependencyProvider<T>
)
