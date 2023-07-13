package app.meetacy.di.android.compose

import androidx.compose.runtime.Composable
import app.meetacy.di.DI
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

object ComposeDependency {
    @Composable
    inline operator fun <reified T> getValue(thisRef: DI, property: KProperty<*>): T {
        return thisRef.remember { get(typeOf<T>(), property.name) }
    }
}
