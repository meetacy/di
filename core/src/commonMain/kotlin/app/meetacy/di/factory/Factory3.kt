package app.meetacy.di.factory

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import app.meetacy.di.internal.LazyFactory
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface Factory3<in T1, in T2, in T3, out T> {
    public fun create(arg1: T1, arg2: T2, arg3: T3): T
}

// DI Extensions

public fun <T1, T2, T3, R> DI.create(
    factoryType: KType,
    arg1: T1,
    arg2: T2,
    arg3: T3,
    name: String? = null
): R = get<Factory3<T1, T2, T3, R>>(
    type = factoryType,
    name = name
).create(arg1, arg2, arg3)

public inline fun <reified T1, reified T2, reified T3, reified R> DI.create(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    name: String? = null
): R = create(
    factoryType = typeOf<Factory3<T1, T2, T3, R>>(),
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    name = name
)

// DI Delegates

public inline fun <reified T1, reified T2, reified T3, reified R> DI.creating(
    arg1: T1,
    arg2: T2,
    arg3: T3
): Factory3Dependency<T1, T2, T3, R> = Factory3Dependency(
    di = this,
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    factoryType = typeOf<Factory2<T1, T2, R>>()
)

public class Factory3Dependency<T1, T2, T3, R>(
    private val di: DI,
    private val arg1: T1,
    private val arg2: T2,
    private val arg3: T3,
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
            arg2 = arg2,
            arg3 = arg3,
            name = property.name
        )
    }
}

// DIBuilder Extensions

public inline fun <reified T1, reified T2, reified T3, reified R> DIBuilder.factory3(
    name: String? = null,
    crossinline factory: DI.(arg1: T1, arg2: T2, arg3: T3) -> R
) {
    provider(name) {
        Factory3 { arg1: T1, arg2: T2, arg3: T3 -> factory(arg1, arg2, arg3) }
    }
}

public inline fun <reified T1, reified T2, reified T3, reified R> DIBuilder.factory3(
    crossinline factory: DI.(arg1: T1, arg2: T2, arg3: T3) -> R
): DIBuilderProviderDelegate<Factory3<T1, T2, T3, R>> = provider {
    Factory3 { arg1: T1, arg2: T2, arg3: T3 -> factory(arg1, arg2, arg3) }
}
