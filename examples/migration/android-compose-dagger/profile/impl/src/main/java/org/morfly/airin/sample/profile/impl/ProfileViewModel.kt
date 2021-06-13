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