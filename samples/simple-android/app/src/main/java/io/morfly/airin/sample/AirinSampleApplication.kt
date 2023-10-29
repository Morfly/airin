package io.morfly.airin.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// https://github.com/google/dagger/issues/4075
@HiltAndroidApp(Application::class)
class AirinSampleApplication : Hilt_AirinSampleApplication()
