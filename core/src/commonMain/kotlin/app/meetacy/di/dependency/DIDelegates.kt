package app.meetacy.di.dependency

import app.meetacy.di.DI
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

public object Dependency {
    public inline operator fun <reified T> getValue(
        thisRef: DI,
        property: KProperty<*>
    ): T = thisRef.get(typeOf<T>(), property.name)
}

public class InnerDependency(public val di: DI) {
    public inline operator fun <reified T> getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): T = di.get(typeOf<T>(), property.name)
}
