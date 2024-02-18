package app.meetacy.di.android.compose.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavController
import app.meetacy.di.DI
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency

public val DI.navController: NavController by Dependency

@PublishedApi
internal val LocalNavController: ProvidableCompositionLocal<NavController> =
    compositionLocalOf {
        error("Please call `buildNavigationDI(...)` only inside `NavigationScreen { ... }`")
    }

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

public fun buildNavigationDI(di: DI): DI = di + di {
    val navController by provider { LocalNavController.current }
}
