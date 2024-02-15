package app.meetacy.di.android.compose.factory

import androidx.compose.runtime.Composable
import app.meetacy.di.DI
import app.meetacy.di.android.compose.internal.LazyFactory
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface ComposableFactory4<in T1, in T2, in T3, in T4, out R> {
    @Composable
    public fun create(arg1: T1, arg2: T2, arg3: T3, arg4: T4): R
}

// DI Extensions

@Composable
public fun <T1, T2, T3, T4, R> DI.createComposable(
    factoryType: KType,
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    name: String? = null
): R = get<ComposableFactory4<T1, T2, T3, T4, R>>(
    type = factoryType,
    name = name
).create(arg1, arg2, arg3, arg4)

@Composable
public inline fun <T1, T2, T3, T4, reified R> DI.createComposable(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    name: String? = null
): R = createComposable(
    factoryType = typeOf<ComposableFactory4<T1, T2, T3, T4, R>>(),
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    arg4 = arg4,
    name = name
)

// DI Delegates

public inline fun <T1, T2, T3, T4, reified R> DI.creatingComposable(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4
): ComposableFactory4Dependency<T1, T2, T3, T4, R> = ComposableFactory4Dependency(
    di = this,
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    arg4 = arg4,
    factoryType = typeOf<ComposableFactory4<T1, T2, T3, T4, R>>()
)

public class ComposableFactory4Dependency<T1, T2, T3, T4, R>(
    private val di: DI,
    private val arg1: T1,
    private val arg2: T2,
    private val arg3: T3,
    private val arg4: T4,
    private val factoryType: KType
) {
    private val lazy = LazyFactory<R>()

    @Composable
    public operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): R = lazy.get { di.createComposable(factoryType, arg1, arg2, arg3, arg4, property.name) }
}

// DIBuilder Extensions

public inline fun <T1, T2, T3, T4, reified R> DIBuilder.composableFactory4(
    name: String? = null,
    crossinline factory: @Composable DI.(T1, T2, T3, T4) -> R
) {
    provider(name) {
        ComposableFactory4 { arg1: T1, arg2: T2, arg3: T3, arg4: T4 -> factory(arg1, arg2, arg3, arg4) }
    }
}

public inline fun <T1, T2, T3, T4, reified R> DIBuilder.composableFactory4(
    crossinline factory: @Composable DI.(T1, T2, T3, T4) -> R
): DIBuilderProviderDelegate<ComposableFactory4<T1, T2, T3, T4, R>> = provider {
    ComposableFactory4 { arg1: T1, arg2: T2, arg3: T3, arg4: T4 -> factory(arg1, arg2, arg3, arg4) }
}
