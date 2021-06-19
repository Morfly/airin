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

package org.morfly.airin.sample.profile.impl

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.morfly.airin.sample.core.ImagesRepository
import org.morfly.airin.sample.core.di.lib.ScreenScoped
import org.morfly.airin.sample.core.entity.Image
import org.morfly.airin.sample.profile.impl.di.UserId
import javax.inject.Inject


@ScreenScoped
class ProfileViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository,
    @UserId private val userId: Long,
) : ViewModel() {

    val avatarUrl = mutableStateOf<String?>(null)
    val username = mutableStateOf<String?>(null)

    init {
        require(userId > 0) { "User id must be non-negative but was $userId." }
    }

    fun userImages(): Flow<List<Image>> =
        imagesRepository.getUserImages(userId).onEach {
            if (it.isNotEmpty()) with(it.first().user) {
                avatarUrl.value = imageUrl
                username.value = name
            }
        }
}