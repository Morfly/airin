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