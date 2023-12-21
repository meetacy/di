package app.meetacy.di.internal

import java.util.concurrent.atomic.AtomicReference

internal actual class AtomicReference<T> actual constructor(value: T) {
    private val delegate = AtomicReference(value)

    actual var value: T
        get() = delegate.get()
        set(value) = delegate.set(value)

    actual fun compareAndSet(expected: T, new: T): Boolean =
        delegate.compareAndSet(expected, new)
}
