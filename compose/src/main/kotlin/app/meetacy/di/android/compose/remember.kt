package app.meetacy.di.android.compose

import androidx.compose.runtime.Composable
import app.meetacy.di.DI

@Composable
inline fun <T> DI.remember(crossinline calculation: DI.() -> T): T {
    return androidx.compose.runtime.remember { calculation() }
}