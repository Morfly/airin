package org.morfly.airin.sample.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.morfly.airin.sample.core.ui.glow


@Composable
fun GlowingMenuIcon(
    isGlowing: Boolean,
    glowingIcon: ImageVector,
    idleIcon: ImageVector,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.White,
    glowColor: Color = iconColor,
) {
    val icon = if (isGlowing) glowingIcon else idleIcon

    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier
            .padding(15.dp)
            .glow(
                color = glowColor,
                alpha = if (isGlowing) 0.45f else 0f,
                radius = 20.dp
            ),
        tint = iconColor
    )
}