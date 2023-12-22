package app.meetacy.di.dependency

import app.meetacy.di.DI
import app.meetacy.di.internal.LazyFactory
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

public object Dependency {
    public inline operator fun <reified T> getValue(
        thisRef: DI,
        property: KProperty<*>
    ): T = thisRef.get(typeOf<T>(), property.name)
}

@Suppress("UNCHECKED_CAST")
public class InnerDependency(public val di: DI) {
    private val lazy = LazyFactory<Any?>()

    public fun <T> getValue(property: KProperty<*>, type: KType): T {
        return lazy.get { di.get(type, property.name) } as T
    }

    public inline operator fun <reified T> getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): T = getValue(property, typeOf<T>())
}
