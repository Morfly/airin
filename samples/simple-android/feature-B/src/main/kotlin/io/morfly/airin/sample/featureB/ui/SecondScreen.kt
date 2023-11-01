package io.morfly.airin.sample.featureB.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    viewModel: SecondViewModel = hiltViewModel()
) {
    Text(
        text = "Second Screen",
        Modifier.clickable { onNextClick() }
    )
}