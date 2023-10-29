package io.morfly.airin.sample.featureA.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    viewModel: FirstViewModel = hiltViewModel()
) {
    Text(
        text = "FirstScreen",
        Modifier.clickable { onNextClick() }
    )
}