package app.meetacy.di.android.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.builder.di
import app.meetacy.di.dependency.Dependency
import app.meetacy.di.factory.create
import app.meetacy.di.factory.factory0
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.typeOf

public val DI.viewModelScope: CoroutineScope by Dependency

@Composable
public inline fun <reified R : Any> DI.viewModel(name: String? = null): R = viewModel { create<R>(name) }

@Composable
public inline fun <reified T1, reified R : Any> DI.viewModel(
    arg1: T1,
    name: String? = null
): R = viewModel { create<T1, R>(arg1, name) }

@Composable
public inline fun <reified T1, reified T2, reified R : Any> DI.viewModel(
    arg1: T1,
    arg2: T2,
    name: String? = null
): R = viewModel { create<T1, T2, R>(arg1, arg2, name) }

@Composable
public inline fun <reified T1, reified T2, reified T3, reified R : Any> DI.viewModel(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    name: String? = null
): R = viewModel { create<T1, T2, T3, R>(arg1, arg2, arg3, name) }

@Composable
public inline fun <reified T1, reified T2, reified T3, reified T4, reified R : Any> DI.viewModel(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    name: String? = null
): R = viewModel { create<T1, T2, T3, T4, R>(arg1, arg2, arg3, arg4, name) }

@Composable
public inline fun <reified T1, reified T2, reified T3, reified T4, reified T5, reified R : Any> DI.viewModel(
    arg1: T1,
    arg2: T2,
    arg3: T3,
    arg4: T4,
    arg5: T5,
    name: String? = null
): R = viewModel { create<T1, T2, T3, T4, T5, R>(arg1, arg2, arg3, arg4, arg5, name) }

@Composable
public inline fun <reified T : Any> DI.viewModel(crossinline factory: DI.() -> T): T {
    return androidx.lifecycle.viewmodel.compose.viewModel<DIViewModel<T>>(key = "${typeOf<T>()}") unused@ {
        val vm = object : DIViewModel<T>() {}

        val di = this@viewModel + di {
            val viewModelScope by constant(vm.viewModelScope)
        }

        vm.apply {
            underlying = di.factory()
        }
    }.underlying
}

@PublishedApi
internal open class DIViewModel<T : Any> : ViewModel() {
    lateinit var underlying: T
}
