package app.meetacy.di

import app.meetacy.di.annotation.DIDsl
import app.meetacy.di.dependency.*
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@DIDsl
public class DI private constructor(
    public val dependencies: Dependencies,
    private val trace: DependencyTrace
) {
    private val dependenciesList = dependencies.list

    public constructor(dependencies: Dependencies) : this(
        dependencies = dependencies,
        trace = DependencyTrace.Empty
    )

    @Suppress("UNCHECKED_CAST")
    public fun <T> providerOrNull(key: DependencyKey<T>): DependencyProvider<T>? {
        val dependency = dependenciesList
            .firstOrNull { (currentKey) -> currentKey == key }
            ?: dependenciesList.singleOrNull { (currentKey) -> currentKey.type == key.type }

        return dependency?.provider as DependencyProvider<T>?
    }

    public fun <T> provider(key: DependencyKey<T>): DependencyProvider<T> =
        providerOrNull(key) ?: error("Dependency $key could not be resolved")

    public fun <T> get(
        key: DependencyKey<T>
    ): T {
        checkRecursive(key)
        val provider = provider(key)
        val dependency = DependencyPair(key, provider)
        val subDI = subDI(dependency.key)
        return dependency.provider.getInstance(subDI)
    }

    private fun checkRecursive(dependencyKey: DependencyKey<*>) {
        if (trace.list.any { key -> key == dependencyKey }) {
            val dependencies = trace.list.joinToString(separator = " -> ") { "$it" }
            error("Recursive cycle found, cannot resolve the dependency. Cycle: $dependencies")
        }
    }

    public fun <T> get(
        type: KType,
        name: String? = null
    ): T {
        val key = DependencyKey<T>(type, name)
        return get(key)
    }

    public inline fun <reified T> get(
        name: String? = null
    ): T {
        return get(
            type = typeOf<T>(),
            name = name
        )
    }

    public operator fun plus(other: DI): DI = DI(
        dependencies = Dependencies(list = dependencies.list + other.dependencies.list),
        trace = DependencyTrace(trace.list + other.trace.list)
    )

    public val getting: InnerDependency = InnerDependency(di = this)

    private fun subDI(key: DependencyKey<*>): DI {
        return DI(
            dependencies = dependencies,
            trace = trace.copy(list = trace.list + key)
        )
    }
}
