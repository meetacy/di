package app.meetacy.di.android.compose.internal

internal class LazyFactory<R> {
    private var cached: R? = null

    /**
     * It's not guaranteed that [block] will be called once
     */
    inline fun get(block: () -> R): R {
        val cached = cached ?: block()
        this.cached = cached
        return cached
    }
}
