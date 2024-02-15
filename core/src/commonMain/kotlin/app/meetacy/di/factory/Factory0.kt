package app.meetacy.di.factory

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import app.meetacy.di.internal.LazyFactory
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface Factory0<out R> {
    public fun create(): R
}

// DI Extensions

public fun <R> DI.create(
    factoryType: KType,
    name: String? = null
): R = get<Factory0<R>>(factoryType, name).create()

public inline fun <reified R> DI.create(
    name: String? = null
): R = create(typeOf<Factory0<R>>(), name)

// DI Delegates

public inline fun <reified R> DI.creating(): Factory0Dependency<R> =
    Factory0Dependency(
        di = this,
        factoryType = typeOf<Factory0<R>>()
    )

public class Factory0Dependency<R>(
    private val di: DI,
    private val factoryType: KType
) {
    private val lazy = LazyFactory<R>()

    public operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): R = lazy.get { di.create(factoryType, property.name) }
}

// DIBuilder Extensions

public inline fun <reified R> DIBuilder.factory0(
    name: String? = null,
    crossinline factory: DI.() -> R
) {
    provider(name) {
        Factory0 { factory() }
    }
}

public inline fun <reified R> DIBuilder.factory0(
    crossinline factory: DI.() -> R
): DIBuilderProviderDelegate<Factory0<R>> = provider {
    Factory0 { factory() }
}
