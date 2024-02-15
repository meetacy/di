package app.meetacy.di.android.compose.factory

import androidx.compose.runtime.Composable
import app.meetacy.di.DI
import app.meetacy.di.android.compose.internal.LazyFactory
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface ComposableFactory3<in T1, in T2, in T3, out R> {
    @Composable
    public fun create(arg1: T1, arg2: T2, arg3: T3): R
}

// DI Extensions

@Composable
public fun <T1, T2, T3, R> DI.createComposable(
    factoryType: KType,
    arg1: T1,
    arg2: T2,
    arg3: T3,
    name: String? = null
): R = get<ComposableFactory3<T1, T2, T3, R>>(
    type = factoryType,
    name = name
).create(arg1, arg2, arg3)

@Composable
public inline fun <T1, T2, T3, reified R> DI.createComposable(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    name: String? = null
): R = createComposable(
    factoryType = typeOf<ComposableFactory3<T1, T2, T3, R>>(),
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    name = name
)

// DI Delegates

public inline fun <T1, T2, T3, reified R> DI.creatingComposable(
    arg1: T1,
    arg2: T2,
    arg3: T3
): ComposableFactory3Dependency<T1, T2, T3, R> = ComposableFactory3Dependency(
    di = this,
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    factoryType = typeOf<ComposableFactory3<T1, T2, T3, R>>()
)

public class ComposableFactory3Dependency<T1, T2, T3, R>(
    private val di: DI,
    private val arg1: T1,
    private val arg2: T2,
    private val arg3: T3,
    private val factoryType: KType
) {
    private val lazy = LazyFactory<R>()

    @Composable
    public operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): R = lazy.get { di.createComposable(factoryType, arg1, arg2, arg3, property.name) }
}

// DIBuilder Extensions

public inline fun <T1, T2, T3, reified R> DIBuilder.composableFactory3(
    name: String? = null,
    crossinline factory: @Composable DI.(T1, T2, T3) -> R
) {
    provider(name) {
        ComposableFactory3 { arg1: T1, arg2: T2, arg3: T3 -> factory(arg1, arg2, arg3) }
    }
}

public inline fun <T1, T2, T3, reified R> DIBuilder.composableFactory3(
    crossinline factory: @Composable DI.(T1, T2, T3) -> R
): DIBuilderProviderDelegate<ComposableFactory3<T1, T2, T3, R>> = provider {
    ComposableFactory3 { arg1: T1, arg2: T2, arg3: T3 -> factory(arg1, arg2, arg3) }
}
