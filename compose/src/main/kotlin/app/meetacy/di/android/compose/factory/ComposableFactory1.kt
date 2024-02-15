package app.meetacy.di.android.compose.factory

import androidx.compose.runtime.Composable
import app.meetacy.di.DI
import app.meetacy.di.android.compose.internal.LazyFactory
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface ComposableFactory1<in T1, out R> {
    @Composable
    public fun create(arg1: T1): R
}

// DI Extensions

@Composable
public fun <T1, R> DI.createComposable(
    factoryType: KType,
    arg1: T1,
    name: String? = null
): R = get<ComposableFactory1<T1, R>>(
    type = factoryType,
    name = name
).create(arg1)

@Composable
public inline fun <T1, reified R> DI.createComposable(
    arg1: T1,
    name: String? = null
): R = createComposable(
    factoryType = typeOf<ComposableFactory1<T1, R>>(),
    arg1 = arg1,
    name = name
)

// DI Delegates

public inline fun <T1, reified R> DI.creatingComposable(
    arg1: T1
): ComposableFactory1Dependency<T1, R> = ComposableFactory1Dependency(
    di = this,
    arg1 = arg1,
    factoryType = typeOf<ComposableFactory1<T1, R>>()
)

public class ComposableFactory1Dependency<T1, R>(
    private val di: DI,
    private val arg1: T1,
    private val factoryType: KType
) {
    private val lazy = LazyFactory<R>()

    @Composable
    public operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): R = lazy.get { di.createComposable(factoryType, arg1, property.name) }
}

// DIBuilder Extensions

public inline fun <T1, reified R> DIBuilder.composableFactory1(
    name: String? = null,
    crossinline factory: @Composable DI.(T1) -> R
) {
    provider(name) {
        ComposableFactory1 { arg1: T1 -> factory(arg1) }
    }
}

public inline fun <T1, reified R> DIBuilder.composableFactory1(
    crossinline factory: @Composable DI.(T1) -> R
): DIBuilderProviderDelegate<ComposableFactory1<T1, R>> = provider {
    ComposableFactory1 { arg1: T1 -> factory(arg1) }
}
