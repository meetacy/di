package app.meetacy.di.scope

import app.meetacy.di.dependency.DependencyKey
import app.meetacy.di.dependency.DependencyProvider

/**
 * As long as the dependencies do not change, the scope remains.
 * When dependencies change, the new dependency that scoped to this scope
 * will be created.
 */
public data class Scope(
    public val dependency: DependencyProvider<Dependency>
) {
    public data class Dependency(public val underlying: Any?) {
        override fun toString(): String = "Scope.Dependency(underlying=$underlying)"
    }
}

public fun scopeOf(provider: DependencyProvider<*>): Scope {
    return Scope { di -> Scope.Dependency(provider.getInstance(di)) }
}

public fun scopeOf(key: DependencyKey<*>): Scope {
    return Scope { di -> Scope.Dependency(di.get(key)) }
}
