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

package org.morfly.airin.sample.profile.impl.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import org.morfly.airin.sample.profile.impl.ProfileViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val images by viewModel.userImages().collectAsState(initial = emptyList())
    val avatarUrl by remember { viewModel.avatarUrl }
    val username by remember { viewModel.username }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        // avatar
        if (avatarUrl != null) {
            Image(
                painter = rememberCoilPainter(avatarUrl),
                contentDescription = null,
                Modifier
                    .padding(top = 30.dp)
                    .size(105.dp)
                    .clip(RoundedCornerShape(50))
            )
        }
        Spacer(Modifier.height(10.dp))

        // username
        Text(
            text = username ?: "",
            style = MaterialTheme.typography.subtitle2
        )
        Spacer(Modifier.height(30.dp))

        // images
        LazyVerticalGrid(cells = GridCells.Fixed(count = 3)) {
            items(images.size) { index ->
                GridItem(imageUrl = images[index].url)
            }
        }
    }
}