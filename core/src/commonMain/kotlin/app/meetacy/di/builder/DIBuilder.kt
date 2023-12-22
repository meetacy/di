package app.meetacy.di.builder

import app.meetacy.di.DI
import app.meetacy.di.annotation.DIDsl
import app.meetacy.di.checkDependencies
import app.meetacy.di.dependency.Dependencies
import app.meetacy.di.dependency.DependencyKey
import app.meetacy.di.dependency.DependencyPair
import app.meetacy.di.dependency.DependencyProvider
import kotlin.reflect.typeOf

public inline fun di(
    dependencies: Dependencies = Dependencies.Empty,
    checkDependencies: Boolean = true,
    block: DIBuilder.() -> Unit
): DI {
    return DIBuilder(dependencies).apply(block).build().apply {
        if (checkDependencies) checkDependencies()
    }
}

public inline fun di(
    di: DI,
    checkDependencies: Boolean = true,
    block: DIBuilder.() -> Unit
): DI = di(di.dependencies, checkDependencies, block)

@DIDsl
public class DIBuilder(dependencies: Dependencies) {
    private val dependencies: MutableList<DependencyPair<*>> = dependencies.list.toMutableList()

    public fun <T> register(dependency: DependencyPair<T>) {
        if (dependencies.any { (key) -> key == dependency.key }) {
            error("There is already a dependency with the key ${dependency.key}")
        }
        dependencies += dependency
    }

    public fun <T> register(key: DependencyKey<T>, provider: DependencyProvider<T>) {
        register(
            dependency = DependencyPair(key, provider)
        )
    }

    public inline fun <reified T> register(
        name: String? = null,
        provider: DependencyProvider<T>
    ) {
        register(
            key = DependencyKey(typeOf<T>(), name),
            provider = provider
        )
    }

    public inline fun <reified T> constant(
        name: String,
        value: T
    ) {
        register<T>(name) { value }
    }

    public inline fun <reified T> constant(value: T): DIBuilderConstantDelegate<T> {
        return DIBuilderConstantDelegate(
            di = this,
            type = typeOf<T>(),
            value = value
        )
    }

    public inline fun <reified T> singleton(
        name: String? = null,
        crossinline factory: DI.() -> T
    ) {
        val key = DependencyKey<T>(
            type = typeOf<T>(),
            name = name
        )
        val provider = DependencyProvider.Singleton { di -> di.factory() }
        register(key, provider)
    }

    public inline fun <reified T> singleton(
        noinline factory: DI.() -> T
    ): DIBuilderSingletonDelegate<T> {
        return DIBuilderSingletonDelegate(
            di = this,
            type = typeOf<T>(),
            factory = factory
        )
    }

    public fun build(): DI = DI(
        dependencies = Dependencies(dependencies.toList())
    )
}
