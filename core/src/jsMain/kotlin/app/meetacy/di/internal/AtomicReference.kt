package app.meetacy.di.internal

/**
 * For now, JS is single-threaded
 */
internal actual class AtomicReference<T> actual constructor(
    actual var value: T
) {
    actual fun compareAndSet(expected: T, new: T): Boolean {
        if (value !== expected) return false
        value = new
        return true
    }
}
