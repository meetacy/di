package app.meetacy.di.global

import app.meetacy.di.DI

public val di: DI
    get() = diSource ?: error("DI is not initialized, call GlobalDI.init(di)")

private var diSource: DI? = null

public object GlobalDI {
    public fun init(di: DI) {
        if (diSource != null) error("DI can only be initialized once")
        diSource = di
    }
}
