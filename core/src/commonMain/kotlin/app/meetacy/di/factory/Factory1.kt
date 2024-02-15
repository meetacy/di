package app.meetacy.di.factory

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import app.meetacy.di.internal.LazyFactory
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface Factory1<in T1, out R> {
    public fun create(arg1: T1): R
}

// DI Extensions

public fun <T1, R> DI.create(
    factoryType: KType,
    arg1: T1,
    name: String? = null
): R = get<Factory1<T1, R>>(
    type = factoryType,
    name = name
).create(arg1)

public inline fun <reified T1, reified R> DI.create(
    arg1: T1,
    name: String? = null
): R = create(
    factoryType = typeOf<Factory1<T1, R>>(),
    arg1 = arg1,
    name = name
)

// DI Delegates

public inline fun <reified T1, reified R> DI.creating(
    arg1: T1
): Factory1Dependency<T1, R> = Factory1Dependency(
    di = this,
    arg1 = arg1,
    factoryType = typeOf<Factory1<T1, R>>()
)

public class Factory1Dependency<T1, R>(
    private val di: DI,
    private val arg1: T1,
    private val factoryType: KType
) {
    private val lazy = LazyFactory<R>()

    public operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): R = lazy.get {
        di.create(
            factoryType = factoryType,
            arg1 = arg1,
            name = property.name
        )
    }
}

// DIBuilder Extensions

public inline fun <reified T1, reified R> DIBuilder.factory1(
    name: String? = null,
    crossinline factory: DI.(arg1: T1) -> R
) {
    provider(name) {
        Factory1 { arg1: T1 -> factory(arg1) }
    }
}

public inline fun <reified T1, reified R> DIBuilder.factory1(
    crossinline factory: DI.(arg1: T1) -> R
): DIBuilderProviderDelegate<Factory1<T1, R>> = provider {
    Factory1 { arg1: T1 -> factory(arg1) }
}
