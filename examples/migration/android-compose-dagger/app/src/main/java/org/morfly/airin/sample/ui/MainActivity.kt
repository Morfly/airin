package org.morfly.airin.sample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import org.morfly.airin.sample.AirinSampleApplication
import org.morfly.airin.sample.NavigationBar
import org.morfly.airin.sample.StatusBar
import org.morfly.airin.sample.core.di.LocalCoreProvider
import org.morfly.airin.sample.core.ui.theme.AirinSampleTheme
import org.morfly.airin.sample.core.ui.theme.AppBlack
import org.morfly.airin.sample.data.LocalDataProvider
import org.morfly.airin.sample.di.LocalAppProvider


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appProvider = (application as AirinSampleApplication).appProvider

        setContent {
            AirinSampleTheme {
                StatusBar(window, color = MaterialTheme.colors.background)

                Surface(color = MaterialTheme.colors.background) {
                    CompositionLocalProvider(
                        LocalAppProvider provides appProvider,
                        LocalDataProvider provides appProvider,
                        LocalCoreProvider provides appProvider,
                    ) {
                        Navigation(onBackPressedDispatcher)
                    }
                }

                NavigationBar(window, color = AppBlack)
            }
        }
    }
}