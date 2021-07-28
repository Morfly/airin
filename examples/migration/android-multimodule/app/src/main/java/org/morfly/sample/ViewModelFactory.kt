package org.morfly.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory<VM : ViewModel> @Inject constructor(
    private val viewModel: Provider<VM>
) : ViewModelProvider.Factory {

    override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
        @Suppress("UNCHECKED_CAST")
        return viewModel.get() as VM
    }
}