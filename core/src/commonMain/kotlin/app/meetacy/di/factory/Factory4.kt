package app.meetacy.di.factory

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderSingletonDelegate
import app.meetacy.di.internal.LazyFactory
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface Factory4<R1, R2, R3, R4, T> {
    public fun create(arg1: R1, arg2: R2, arg3: R3, arg4: R4): T
}

// DI Extensions

public fun <T1, T2, T3, T4, R> DI.create(
    factoryType: KType,
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    name: String? = null
): R = get<Factory4<T1, T2, T3, T4, R>>(
    type = factoryType,
    name = name
).create(arg1, arg2, arg3, arg4)

public inline fun <reified T1, reified T2, reified T3, reified T4, reified R> DI.create(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    name: String? = null
): R = create(
    factoryType = typeOf<Factory4<T1, T2, T3, T4, R>>(),
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    arg4 = arg4,
    name = name
)

// DI Delegates

public inline fun <reified T1, reified T2, reified T3, reified T4, reified R> DI.creating(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4
): Factory4Dependency<T1, T2, T3, T4, R> = Factory4Dependency(
    di = this,
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    arg4 = arg4,
    factoryType = typeOf<Factory2<T1, T2, R>>()
)

public class Factory4Dependency<T1, T2, T3, T4, R>(
    private val di: DI,
    private val arg1: T1,
    private val arg2: T2,
    private val arg3: T3,
    private val arg4: T4,
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
            name = property.name
        )
    }
}

// DIBuilder Extensions

public inline fun <reified T1, reified T2, reified T3, reified T4, reified R> DIBuilder.factory4(
    name: String? = null,
    crossinline factory: DI.(arg1: T1, arg2: T2, arg3: T3, arg4: T4) -> R
) {
    singleton(name) {
        Factory4 { arg1: T1, arg2: T2, arg3: T3, arg4: T4 -> factory(arg1, arg2, arg3, arg4) }
    }
}

public inline fun <reified T1, reified T2, reified T3, reified T4, reified R> DIBuilder.factory4(
    crossinline factory: DI.(arg1: T1, arg2: T2, arg3: T3, arg4: T4) -> R
): DIBuilderSingletonDelegate<Factory4<T1, T2, T3, T4, R>> = singleton {
    Factory4 { arg1: T1, arg2: T2, arg3: T3, arg4: T4 -> factory(arg1, arg2, arg3, arg4) }
}
