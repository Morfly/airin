package org.morfly.airin.sample.imagelist.impl.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import org.morfly.airin.sample.core.ui.theme.AppBlue
import org.morfly.airin.sample.imagelist.impl.ImageListViewModel


@Composable
fun ImageList(viewModel: ImageListViewModel) {
    val images = viewModel.images.collectAsLazyPagingItems()
    val searchSuggestion = remember { viewModel.searchSuggestion }

    Box {
        LazyColumn {
            item { Spacer(Modifier.height(15.dp)) }
            items(images.itemCount) { index ->
                images.getAsState(index).value?.let { image ->
                    ImageItem(image)
                }
            }
            item { Spacer(Modifier.height(70.dp)) }
        }
        if (images.itemCount == 0) Empty(searchSuggestion)
        Loading(loadState = images.loadState)
    }
}

@Composable
private fun Loading(loadState: CombinedLoadStates) = with(loadState) {
    when (LoadState.Loading) {
        refresh -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    Modifier.size(50.dp),
                    color = AppBlue,
                    strokeWidth = 5.dp
                )
            }
        }
    }
}

@Composable
private fun Empty(searchSuggestion: String) {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Try searching \"$searchSuggestion\"",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 30.dp)
        )
    }
}