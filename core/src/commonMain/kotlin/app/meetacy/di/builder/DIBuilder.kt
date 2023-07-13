package app.meetacy.backend.di.builder

import app.meetacy.backend.di.DI
import app.meetacy.backend.di.annotation.DIDsl
import app.meetacy.backend.di.checkDependencies
import app.meetacy.backend.di.dependency.Dependencies
import app.meetacy.backend.di.dependency.DependencyKey
import app.meetacy.backend.di.dependency.DependencyPair
import app.meetacy.backend.di.dependency.DependencyProvider
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

    public inline fun <reified T> constant(
        name: String,
        value: T
    ) {
        factory(name) { value }
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


    public inline fun <reified T> factory(
        name: String? = null,
        crossinline factory: DI.() -> T
    ) {
        val key = DependencyKey<T>(
            type = typeOf<T>(),
            name = name
        )
        val provider = DependencyProvider { di -> di.factory() }
        register(key, provider)
    }

    public inline fun <reified T> factory(
        noinline factory: DI.() -> T
    ): DIBuilderFactoryDelegate<T> {
        return DIBuilderFactoryDelegate(
            di = this,
            type = typeOf<T>(),
            factory = factory
        )
    }

    public fun build(): DI = DI(
        dependencies = Dependencies(dependencies.toList())
    )
}
