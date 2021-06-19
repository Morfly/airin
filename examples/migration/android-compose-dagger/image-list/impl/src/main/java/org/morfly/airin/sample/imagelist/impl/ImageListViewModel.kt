/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.morfly.airin.sample.imagelist.impl

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

    val searchSuggestion: String =
        SEARCH_SUGGESTIONS.random()

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
    }

    fun userImages(id: Long): Flow<List<Image>> =
        imagesRepository.getUserImages(userId = id)


    companion object {

        private const val QUERY_INPUT_DELAY_MILLIS = 1000L

        private val SEARCH_SUGGESTIONS = listOf(
            "kitten",
            "puppies",
        )
    }
}