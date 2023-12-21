package app.meetacy.di.internal

import kotlin.native.concurrent.AtomicReference

internal actual class AtomicReference<T> actual constructor(
    value: T
) {
    private val delegate = AtomicReference(value)

    actual var value: T by delegate::value

    actual fun compareAndSet(expected: T, new: T): Boolean =
        delegate.compareAndSet(expected, new)
}
