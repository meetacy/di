@file:Suppress("NAME_SHADOWING")

package app.meetacy.di.android

import android.app.Application
import app.meetacy.di.DI
import app.meetacy.di.android.annotation.AndroidGlobalApi
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency
import app.meetacy.di.global.GlobalDI
import app.meetacy.di.global.annotation.GlobalApi
import app.meetacy.di.global.di

public val di: DI get() {
    return try {
        di
    } catch (_: IllegalStateException) {
        error("DI is not initialized, call AndroidDI.init(di)")
    }
}

public val DI.application: Application by Dependency

@AndroidGlobalApi
public object AndroidDI {
    @OptIn(GlobalApi::class)
    public fun init(
        application: Application,
        di: DI
    ) {
        GlobalDI.init(
            di = di + di(checkDependencies = false) {
                val application by constant(application)
            }
        )
    }
}
