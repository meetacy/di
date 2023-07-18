package app.meetacy.di.dependency.builder

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.DependencyKey
import app.meetacy.di.dependency.DependencyProvider
import java.lang.ref.WeakReference
import kotlin.reflect.typeOf

public class WeakDependencyProvider<out T>(
    private val base: DependencyProvider<T>
) : DependencyProvider<T> {
    private var reference: WeakReference<T>? = null

    override fun createNewInstance(di: DI): T {
        val oldValue = reference?.get()
        if (oldValue != null) return oldValue
        synchronized(this) {
            val value = reference?.get()
            if (value != null) return value

            val newValue = base.createNewInstance(di)
            reference = WeakReference(newValue)
            return newValue
        }
    }
}

public inline fun <reified T> DIBuilder.weak(
    name: String? = null,
    crossinline factory: DI.() -> T
) {
    val key = DependencyKey<T>(typeOf<T>(), name)
    val dependency = WeakDependencyProvider { di -> di.factory() }
    register(key, dependency)
}
