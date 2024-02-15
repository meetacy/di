package app.meetacy.di.android.compose.factory

import androidx.compose.runtime.Composable
import app.meetacy.di.DI
import app.meetacy.di.android.compose.internal.LazyFactory
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.DIBuilderProviderDelegate
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public fun interface ComposableFactory0<out R> {
    @Composable
    public fun create(): R
}

// DI Extensions

@Composable
public fun <R> DI.createComposable(
    factoryType: KType,
    name: String? = null
): R = get<ComposableFactory0<R>>(factoryType, name).create()

@Composable
public inline fun <reified R> DI.createComposable(
    name: String? = null
): R = createComposable(typeOf<ComposableFactory0<R>>(), name)

// DI Delegates

public inline fun <reified R> DI.creatingComposable(): ComposableFactory0Dependency<R> =
    ComposableFactory0Dependency(
        di = this,
        factoryType = typeOf<ComposableFactory0<R>>()
    )

public class ComposableFactory0Dependency<R>(
    private val di: DI,
    private val factoryType: KType
) {
    private val lazy = LazyFactory<R>()

    @Composable
    public operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): R = lazy.get { di.createComposable(factoryType, property.name) }
}

// DIBuilder Extensions

public inline fun <reified R> DIBuilder.composableFactory0(
    name: String? = null,
    crossinline factory: @Composable DI.() -> R
) {
    provider(name) {
        ComposableFactory0 { factory() }
    }
}

public inline fun <reified R> DIBuilder.composableFactory0(
    crossinline factory: @Composable DI.() -> R
): DIBuilderProviderDelegate<ComposableFactory0<R>> = provider {
    ComposableFactory0 { factory() }
}

