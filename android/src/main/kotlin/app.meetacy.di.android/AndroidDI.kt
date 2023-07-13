@file:Suppress("UNUSED_VARIABLE", "NAME_SHADOWING")

package app.meetacy.di.android

import android.app.Application
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency

lateinit var di: DI private set

val DI.application: Application by Dependency

public object AndroidDI {
    public fun build(application: Application, builder: DIBuilder.() -> Unit) {
        di = di {
            val application by constant(application)
            builder()
        }
    }
}
