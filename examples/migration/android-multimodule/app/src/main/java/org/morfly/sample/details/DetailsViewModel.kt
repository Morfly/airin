package org.morfly.sample.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.morfly.sample.imagedata.Image
import javax.inject.Inject


class DetailsViewModel @Inject constructor(image: Image) : ViewModel() {

    val viewState: StateFlow<DetailsViewState> = MutableStateFlow(
        DetailsViewState.Image(image)
    )
}