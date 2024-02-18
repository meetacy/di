@file:Suppress("NAME_SHADOWING")

package app.meetacy.di.android.compose.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavController
import app.meetacy.di.DI
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency

public val DI.navController: NavController by Dependency

@PublishedApi
internal val LocalNavController: ProvidableCompositionLocal<NavController?> = compositionLocalOf { null }

@Composable
public fun NavigationScreen(
    controller: NavController,
    block: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalNavController provides controller,
        content = block
    )
}

@Composable
public fun buildNavigationDI(base: DI): DI {
    val navController = LocalNavController.current

    return base + di {
        val navController by provider {
            navController ?: error("Please call `buildNavigationDI(...)` only inside `NavigationScreen { ... }`")
        }
    }
}
