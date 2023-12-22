package app.meetacy.di.factory

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderSingletonDelegate
import app.meetacy.di.internal.LazyFactory
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface Factory2<R1, R2, T> {
    public fun create(arg1: R1, arg2: R2): T
}

// DI Extensions

public fun <T1, T2, R> DI.create(
    factoryType: KType,
    arg1: T1,
    arg2: T2,
    name: String? = null
): R = get<Factory2<T1, T2, R>>(
    type = factoryType,
    name = name
).create(arg1, arg2)

public inline fun <reified T1, reified T2, reified R> DI.create(
    arg1: T1,
    arg2: T2,
    name: String? = null
): R = create(
    factoryType = typeOf<Factory2<T1, T2, R>>(),
    arg1 = arg1,
    arg2 = arg2,
    name = name
)

// DI Delegates

public inline fun <reified T1, reified T2, reified R> DI.creating(
    arg1: T1,
    arg2: T2
): Factory2Dependency<T1, T2, R> = Factory2Dependency(
    di = this,
    arg1 = arg1,
    arg2 = arg2,
    factoryType = typeOf<Factory2<T1, T2, R>>()
)

public class Factory2Dependency<T1, T2, R>(
    private val di: DI,
    private val arg1: T1,
    private val arg2: T2,
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
            name = property.name
        )
    }
}

// DIBuilder Extensions

public inline fun <reified T1, reified T2, reified R> DIBuilder.factory2(
    name: String? = null,
    crossinline factory: DI.(arg1: T1, arg2: T2) -> R
) {
    singleton(name) {
        Factory2 { arg1: T1, arg2: T2 -> factory(arg1, arg2) }
    }
}

public inline fun <reified T1, reified T2, reified R> DIBuilder.factory2(
    crossinline factory: DI.(arg1: T1, arg2: T2) -> R
): DIBuilderSingletonDelegate<Factory2<T1, T2, R>> = singleton {
    Factory2 { arg1: T1, arg2: T2 -> factory(arg1, arg2) }
}
