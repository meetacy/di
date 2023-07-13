package app.meetacy.di.dependency

import app.meetacy.di.DI

public fun interface DependencyProvider<out T> {

    public fun createNewInstance(di: DI): T

    public class Singleton<out T>(
        private val base: DependencyProvider<T>
    ) : DependencyProvider<T> {
        private lateinit var di: DI
        private val value by lazy { base.createNewInstance(di) }

        override fun createNewInstance(di: DI): T {
            this.di = di
            return value
        }
    }
}
