package app.meetacy.di.internal

internal expect class AtomicReference<T>(value: T) {
    var value: T
    fun compareAndSet(expected: T, new: T): Boolean
}

internal inline fun <T, T2 : T> AtomicReference<T>.updateAndGet(transform: (T) -> T2): T2 {
    while (true) {
        val new = transform(value)
        if (compareAndSet(value, new)) return new
    }
}
