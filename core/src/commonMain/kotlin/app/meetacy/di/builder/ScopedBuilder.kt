package app.meetacy.di.builder

import app.meetacy.di.DI
import app.meetacy.di.dependency.DependencyProvider
import app.meetacy.di.scope.Scope
import app.meetacy.di.scope.scopeOf

public class ScopedBuilder<T> {
    private var scope: Scope? = null
    private var factory: DependencyProvider<T>? = null

    public fun keepWhile(
        block: ScopeBuilder.() -> Scope.Dependency
    ) {
        scope = scopeOf { ScopeBuilder(it).run(block) }
    }

    public fun factory(
        block: DI.() -> T
    ) {
        factory = DependencyProvider(block)
    }

    public fun build(): DependencyProvider<T> = DependencyProvider.Scoped(
        scope ?: error("Scope was not initialized, call keepWhile function to setup scope"),
        factory ?: error("Factory was not initialized, call factory function to setup factory")
    )
}

public class ScopeBuilder(public val di: DI) {
    public fun retained(vararg any: Any?): Scope.Dependency = Scope.Dependency(any.toList())
}
