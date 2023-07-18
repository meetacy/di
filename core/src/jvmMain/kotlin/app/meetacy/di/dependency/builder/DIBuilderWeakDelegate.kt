package app.meetacy.di.dependency.builder

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.DependencyKey
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public class DIBuilderWeakDelegate<T>(
    private val di: DIBuilder,
    private val type: KType,
    private val factory: DI.() -> T
) {
    public operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, Unit> {
        di.register(
            key = DependencyKey(type, property.name),
            provider = WeakDependencyProvider { di -> di.factory() }
        )
        return ReadOnlyProperty { _, _ -> }
    }
}

public inline fun <reified T> DIBuilder.weak(
    crossinline factory: DI.() -> T
): DIBuilderWeakDelegate<T> = DIBuilderWeakDelegate(
    di = this,
    type = typeOf<T>(),
    factory = { factory() }
)
