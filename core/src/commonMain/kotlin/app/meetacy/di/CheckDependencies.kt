package app.meetacy.di

import app.meetacy.di.dependency.Dependencies

/**
 * Throws if some dependencies are inaccessible.
 * This eliminates cons of having service locator
 * instead of compile-time DI
 */
public fun Dependencies.checkDependencies() {
    val di = DI(dependencies = this)

    for (dependency in di.dependencies.list) {
        di.get(dependency.key)
    }
}

public fun DI.checkDependencies() {
    dependencies.checkDependencies()
}
