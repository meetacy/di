package app.meetacy.backend.di.dependency

import kotlin.reflect.KType

public data class DependencyKey<T>(
    val type: KType,
    val name: String? = null
) {
    override fun toString(): String {
        return if (name == null) {
            "DependencyKey{$type}"
        } else {
            "DependencyKey{$name: $type}"
        }
    }
}
