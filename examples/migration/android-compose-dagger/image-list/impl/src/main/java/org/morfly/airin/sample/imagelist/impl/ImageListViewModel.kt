package org.morfly.airin.sample.imagelist.impl

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.morfly.airin.sample.core.ImagesRepository
import org.morfly.airin.sample.core.di.lib.ScreenScoped
import org.morfly.airin.sample.core.entity.Image
import javax.inject.Inject


private const val QUERY_INPUT_DELAY_MILLIS = 1000L

@ScreenScoped
class ImageListViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository
) : ViewModel() {

    private val searchQueryFlow = MutableStateFlow<String?>(null)
    
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val images = searchQueryFlow
        .filterNotNull()
        .debounce(QUERY_INPUT_DELAY_MILLIS)
        .flatMapLatest { imagesRepository.getPagedImages(query = it) }
        .cachedIn(viewModelScope)

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
    }

    fun userImages(id: Long): Flow<List<Image>> =
        imagesRepository.getUserImages(userId = id)
}