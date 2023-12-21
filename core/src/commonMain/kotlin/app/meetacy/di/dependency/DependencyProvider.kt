package app.meetacy.di.dependency

import app.meetacy.di.DI
import app.meetacy.di.internal.AtomicReference
import app.meetacy.di.internal.updateAndGet
import app.meetacy.di.scope.Scope

public fun interface DependencyProvider<out T> {

    public fun getInstance(di: DI): T

    public class Singleton<out T>(
        private val base: DependencyProvider<T>
    ) : DependencyProvider<T> {
        private lateinit var di: DI
        private val value by lazy { base.getInstance(di) }

        override fun getInstance(di: DI): T {
            this.di = di
            return value
        }
    }

    public class Scoped<out T>(
        private val scope: Scope,
        private val base: DependencyProvider<T>
    ) : DependencyProvider<T> {
        private val reference = AtomicReference<Value<T>?>(value = null)

        override fun getInstance(di: DI): T = reference.updateAndGet { value ->
            val scopeDependency = scope.dependency.getInstance(di)

            // if nothing is initialized, we should initialize one
            if (value == null) {
                val instance = base.getInstance(di)
                return@updateAndGet Value(scopeDependency, instance)
            }

            // if there was already something in there, we should check if scope dependency is the same
            if (value.currentScope == scopeDependency) return@updateAndGet value

            // dependency changed, so we need to refresh factory
            val instance = base.getInstance(di)
            Value(scopeDependency, instance)
        }.instance

        private class Value<out T>(
            val currentScope: Scope.Dependency,
            val instance: T
        )
    }
}
