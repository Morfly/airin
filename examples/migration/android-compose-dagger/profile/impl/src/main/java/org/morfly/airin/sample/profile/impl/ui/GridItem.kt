package org.morfly.airin.sample.profile.impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter


@Composable
fun GridItem(imageUrl: String) {
    Image(
        painter = rememberCoilPainter(imageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20))
    )
}