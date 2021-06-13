package org.morfly.airin.sample.imagelist.impl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import org.morfly.airin.sample.imagelist.impl.ImageListViewModel


@Composable
fun ImageListScreen(viewModel: ImageListViewModel, onUserSelected: (userId: Long) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    Column {
        SearchField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.updateSearchQuery(searchQuery)
            },
            onClick = {
                viewModel.updateSearchQuery(searchQuery)
            }
        )
        ImageList(viewModel, onUserSelected)
    }
}