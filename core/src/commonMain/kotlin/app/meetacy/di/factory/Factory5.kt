package app.meetacy.di.factory

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import app.meetacy.di.internal.LazyFactory
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface Factory5<R1, R2, R3, R4, R5, T> {
    public fun create(arg1: R1, arg2: R2, arg3: R3, arg4: R4, arg5: R5): T
}

// DI Extensions

public fun <T1, T2, T3, T4, T5, R> DI.create(
    factoryType: KType,
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    arg5: T5,
    name: String? = null
): R = get<Factory5<T1, T2, T3, T4, T5, R>>(
    type = factoryType,
    name = name
).create(arg1, arg2, arg3, arg4, arg5)

public inline fun <reified T1, reified T2, reified T3, reified T4, reified T5, reified R> DI.create(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    arg5: T5,
    name: String? = null
): R = create(
    factoryType = typeOf<Factory5<T1, T2, T3, T4, T5, R>>(),
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    arg4 = arg4,
    arg5 = arg5,
    name = name
)

// DI Delegates

public inline fun <reified T1, reified T2, reified T3, reified T4, reified T5, reified R> DI.creating(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    arg5: T5
): Factory5Dependency<T1, T2, T3, T4, T5, R> = Factory5Dependency(
    di = this,
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    arg4 = arg4,
    arg5 = arg5,
    factoryType = typeOf<Factory2<T1, T2, R>>()
)

public class Factory5Dependency<T1, T2, T3, T4, T5, R>(
    private val di: DI,
    private val arg1: T1,
    private val arg2: T2,
    private val arg3: T3,
    private val arg4: T4,
    private val arg5: T5,
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
            arg4 = arg4,
            arg5 = arg5,
            name = property.name
        )
    }
}

// DIBuilder Extensions

public inline fun <reified T1, reified T2, reified T3, reified T4, reified T5, reified R> DIBuilder.factory5(
    name: String? = null,
    crossinline factory: DI.(arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5) -> R
) {
    provider(name) {
        Factory5 { arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5 -> factory(arg1, arg2, arg3, arg4, arg5) }
    }
}

public inline fun <reified T1, reified T2, reified T3, reified T4, reified T5, reified R> DIBuilder.factory5(
    crossinline factory: DI.(arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5) -> R
): DIBuilderProviderDelegate<Factory5<T1, T2, T3, T4, T5, R>> = provider {
    Factory5 { arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5 -> factory(arg1, arg2, arg3, arg4, arg5) }
}
