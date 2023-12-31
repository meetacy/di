package app.meetacy.di.builder

import app.meetacy.di.DI
import app.meetacy.di.dependency.DependencyKey
import app.meetacy.di.dependency.DependencyProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType

public class DIBuilderConstantDelegate<T>(
    private val di: DIBuilder,
    private val type: KType,
    private val value: T
) {
    public operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): ReadOnlyProperty<Any?, Unit> {
        di.register(
            key = DependencyKey(type, property.name),
            provider = { value }
        )
        return ReadOnlyProperty { _, _ -> }
    }
}

public class DIBuilderSingletonDelegate<T>(
    private val di: DIBuilder,
    private val type: KType,
    private val factory: DI.() -> T
) {
    public operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): ReadOnlyProperty<Any?, Unit> {
        di.register(
            key = DependencyKey(type, property.name),
            provider = DependencyProvider.Singleton { di -> di.factory() }
        )
        return ReadOnlyProperty { _, _ -> }
    }
}

public class DIBuilderProviderDelegate<T>(
    private val di: DIBuilder,
    private val type: KType,
    private val factory: DI.() -> T
) {
    public operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): ReadOnlyProperty<Any?, Unit> {
        di.register(
            key = DependencyKey(type, property.name),
            provider = { di -> di.factory() }
        )
        return ReadOnlyProperty { _, _ -> }
    }
}
