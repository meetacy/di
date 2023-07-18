@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.di.android.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.meetacy.di.DI
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.typeOf

val DI.viewModelScope: CoroutineScope by Dependency

@Composable
inline fun <reified T : Any> DI.viewModel(crossinline factory: DI.() -> T) {
    androidx.lifecycle.viewmodel.compose.viewModel(key = "${typeOf<T>()}") unused@ {
        val vm = DIViewModel<T>()

        val di = this@viewModel + di {
            val viewModelScope by constant(vm.viewModelScope)
        }

        vm.apply {
            underlying = di.factory()
        }
    }
}

@PublishedApi
internal class DIViewModel<T : Any> : ViewModel() {
    lateinit var underlying: T
}
