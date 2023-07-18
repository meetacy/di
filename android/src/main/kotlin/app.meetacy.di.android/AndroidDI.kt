@file:Suppress("UNUSED_VARIABLE", "NAME_SHADOWING")

package app.meetacy.di.android

import android.app.Application
import app.meetacy.di.DI
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency
import app.meetacy.di.android.di as globalDi

lateinit var di: DI private set

val DI.application: Application by Dependency

public object AndroidDI {
    public fun inject(
        application: Application,
        di: DI
    ) {
        if (::globalDi.isInitialized) error("DI can only be initialized once")

        globalDi = di + di(checkDependencies = false) {
            val application by constant(application)
        }
    }
}
