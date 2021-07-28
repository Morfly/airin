package org.morfly.sample.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.morfly.sample.imagedata.ImagesRepository
import javax.inject.Inject


class FeedViewModel @Inject constructor(
    imagesRepository: ImagesRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<FeedViewState>(FeedViewState.Idle)
    val viewState: LiveData<FeedViewState> = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = FeedViewState.Loading(viewState.value!!.images)
            val images = imagesRepository.loadImages()
            _viewState.value = FeedViewState.Loaded(images)
        }
    }
}