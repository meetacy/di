package app.meetacy.di.android.compose.factory

import androidx.compose.runtime.Composable
import app.meetacy.di.DI
import app.meetacy.di.android.compose.internal.LazyFactory
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface ComposableFactory5<in T1, in T2, in T3, in T4, in T5, out R> {
    @Composable
    public fun create(arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5): R
}

// DI Extensions

@Composable
public fun <T1, T2, T3, T4, T5, R> DI.createComposable(
    factoryType: KType,
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    arg5: T5,
    name: String? = null
): R = get<ComposableFactory5<T1, T2, T3, T4, T5, R>>(
    type = factoryType,
    name = name
).create(arg1, arg2, arg3, arg4, arg5)

@Composable
public inline fun <T1, T2, T3, T4, T5, reified R> DI.createComposable(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    arg5: T5,
    name: String? = null
): R = createComposable(
    factoryType = typeOf<ComposableFactory5<T1, T2, T3, T4, T5, R>>(),
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    arg4 = arg4,
    arg5 = arg5,
    name = name
)

// DI Delegates

public inline fun <T1, T2, T3, T4, T5, reified R> DI.creatingComposable(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    arg5: T5
): ComposableFactory5Dependency<T1, T2, T3, T4, T5, R> = ComposableFactory5Dependency(
    di = this,
    arg1 = arg1,
    arg2 = arg2,
    arg3 = arg3,
    arg4 = arg4,
    arg5 = arg5,
    factoryType = typeOf<ComposableFactory5<T1, T2, T3, T4, T5, R>>()
)

public class ComposableFactory5Dependency<T1, T2, T3, T4, T5, R>(
    private val di: DI,
    private val arg1: T1,
    private val arg2: T2,
    private val arg3: T3,
    private val arg4: T4,
    private val arg5: T5,
    private val factoryType: KType
) {
    private val lazy = LazyFactory<R>()

    @Composable
    public operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): R = lazy.get { di.createComposable(factoryType, arg1, arg2, arg3, arg4, arg5, property.name) }
}

// DIBuilder Extensions

public inline fun <T1, T2, T3, T4, T5, reified R> DIBuilder.composableFactory5(
    name: String? = null,
    crossinline factory: @Composable DI.(T1, T2, T3, T4, T5) -> R
) {
    provider(name) {
        ComposableFactory5 { arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5 -> factory(arg1, arg2, arg3, arg4, arg5) }
    }
}

public inline fun <T1, T2, T3, T4, T5, reified R> DIBuilder.composableFactory5(
    crossinline factory: @Composable DI.(T1, T2, T3, T4, T5) -> R
): DIBuilderProviderDelegate<ComposableFactory5<T1, T2, T3, T4, T5, R>> = provider {
    ComposableFactory5 { arg1: T1, arg2: T2, arg3: T3, arg4: T4, arg5: T5 -> factory(arg1, arg2, arg3, arg4, arg5) }
}
