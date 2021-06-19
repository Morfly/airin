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

package org.morfly.airin.sample.imagelist.impl.ui

import android.graphics.drawable.Drawable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.DrawablePainter
import com.google.accompanist.imageloading.ImageLoadState
import org.morfly.airin.sample.core.R
import org.morfly.airin.sample.core.ui.findDominantColor
import org.morfly.airin.sample.core.ui.glow
import org.morfly.airin.sample.core.ui.theme.AppBlack
import org.morfly.airin.sample.core.ui.theme.Background
import org.morfly.airin.sample.core.entity.Image as DomainImage


private val userImageSize = 43.dp

@Composable
fun ImageItem(image: DomainImage, onUserSelected: (userId: Long) -> Unit) {
    var glowColor by remember { mutableStateOf(Color.White) }
    val animatedGlowColor by animateColorAsState(
        targetValue = glowColor,
        animationSpec = tween(1500)
    )
    Box(
        modifier = Modifier
            .glow(
                color = animatedGlowColor,
                radius = 25.dp,
                alpha = 0.1f,
            )
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clip(RoundedCornerShape(7))
            .background(Color.White)

    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Row(
                Modifier
                    .height(userImageSize)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberCoilPainter(image.user.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(userImageSize)
                        .clip(RoundedCornerShape(50))
                        .clickable { onUserSelected(image.user.id) }
                )
                Spacer(Modifier.width(10.dp))

                Column(
                    Modifier
                        .padding(vertical = 2.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onUserSelected(image.user.id) }
                ) {
                    Text(text = image.user.name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Text(text = "San Francisco", fontSize = 12.sp, fontWeight = FontWeight.Light)
                }
                Spacer(Modifier.weight(1f))
                Box(Modifier.padding(horizontal = 5.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_more_horiz_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { }
                            .padding(5.dp)
                    )
                }
            }
            Box(
                Modifier
                    .padding(12.dp)
                    .glow(
                        color = animatedGlowColor,
                        radius = 25.dp,
                        alpha = 0.3f,
                        offsetY = 15.dp
                    )
            ) {
                PhotoImage(url = image.url) {
                    glowColor = it
                }
            }
            Row(Modifier.padding(horizontal = 12.dp)) {
                Likes(likes = image.likes.toString())
                Spacer(Modifier.width(7.dp))
                Comments(comments = image.comments.toString())
                Spacer(Modifier.width(7.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 3.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_bookmark_border_24),
                        contentDescription = null,
                        Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { }
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoImage(url: String, onDominantColorLoaded: (Color) -> Unit) {
    var dominantColorFound by remember { mutableStateOf(false) }
    val painter = rememberCoilPainter(
        request = url,
        requestBuilder = { allowHardware(false) },
        fadeIn = true
    )
    if (!dominantColorFound) {
        when (val state = painter.loadState) {
            is ImageLoadState.Success -> {
                val drawable = (state.result as DrawablePainter).drawable()
                val color = drawable.findDominantColor()
                onDominantColorLoaded(color)
                @Suppress("UNUSED_VALUE")
                dominantColorFound = true
            }
        }
    }

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .requiredHeight(240.dp)
    )
}

@Composable
private fun Likes(likes: String) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(Background)
            .width(80.dp)
    ) {
        Row(
            Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = likes,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppBlack
                )
            }
        }
    }
}

@Composable
private fun Comments(comments: String) {
    Row(
        Modifier
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .width(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_round_chat_24),
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = comments,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AppBlack
            )
        }
    }
}

private fun DrawablePainter.drawable(): Drawable {
    val field = javaClass.getDeclaredField("drawable")
    field.isAccessible = true
    return field.get(this) as Drawable
}