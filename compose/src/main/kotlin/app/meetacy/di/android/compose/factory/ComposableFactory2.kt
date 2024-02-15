package app.meetacy.di.android.compose.factory

import androidx.compose.runtime.Composable
import app.meetacy.di.DI
import app.meetacy.di.android.compose.internal.LazyFactory
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface ComposableFactory2<in T1, in T2, out R> {
    @Composable
    public fun create(arg1: T1, arg2: T2): R
}

// DI Extensions

@Composable
public fun <T1, T2, R> DI.createComposable(
    factoryType: KType,
    arg1: T1,
    arg2: T2,
    name: String? = null
): R = get<ComposableFactory2<T1, T2, R>>(
    type = factoryType,
    name = name
).create(arg1, arg2)

@Composable
public inline fun <T1, T2, reified R> DI.createComposable(
    arg1: T1,
    arg2: T2,
    name: String? = null
): R = createComposable(
    factoryType = typeOf<ComposableFactory2<T1, T2, R>>(),
    arg1 = arg1,
    arg2 = arg2,
    name = name
)

// DI Delegates

public inline fun <T1, T2, reified R> DI.creatingComposable(
    arg1: T1,
    arg2: T2
): ComposableFactory2Dependency<T1, T2, R> = ComposableFactory2Dependency(
    di = this,
    arg1 = arg1,
    arg2 = arg2,
    factoryType = typeOf<ComposableFactory2<T1, T2, R>>()
)

public class ComposableFactory2Dependency<T1, T2, R>(
    private val di: DI,
    private val arg1: T1,
    private val arg2: T2,
    private val factoryType: KType
) {
    private val lazy = LazyFactory<R>()

    @Composable
    public operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): R = lazy.get { di.createComposable(factoryType, arg1, arg2, property.name) }
}

// DIBuilder Extensions

public inline fun <T1, T2, reified R> DIBuilder.composableFactory2(
    name: String? = null,
    crossinline factory: @Composable DI.(T1, T2) -> R
) {
    provider(name) {
        ComposableFactory2 { arg1: T1, arg2: T2 -> factory(arg1, arg2) }
    }
}

public inline fun <T1, T2, reified R> DIBuilder.composableFactory2(
    crossinline factory: @Composable DI.(T1, T2) -> R
): DIBuilderProviderDelegate<ComposableFactory2<T1, T2, R>> = provider {
    ComposableFactory2 { arg1: T1, arg2: T2 -> factory(arg1, arg2) }
}
