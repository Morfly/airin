package org.morfly.airin.sample.core.di.lib

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
inline fun <reified T : ViewModel> injectedViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> T
): T = viewModel(
    modelClass = T::class.java,
    key = key,
    factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return viewModelInstanceCreator() as T
        }
    }
)