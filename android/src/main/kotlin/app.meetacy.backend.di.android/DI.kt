@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.di.android

import android.app.Application
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.builder.di
import app.meetacy.backend.di.dependency.Dependency

lateinit var di: DI private set

val DI.application: Application by Dependency

public object DI {
    public fun Application.build(builder: DIBuilder.() -> Unit) {
        di = di {
            val application by constant(this@build)
            builder()
        }
    }
}
