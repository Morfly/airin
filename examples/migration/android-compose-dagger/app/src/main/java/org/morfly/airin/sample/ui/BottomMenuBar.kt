package org.morfly.airin.sample.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.morfly.airin.sample.core.FeatureEntries
import org.morfly.airin.sample.core.route
import org.morfly.airin.sample.core.ui.glow
import org.morfly.airin.sample.core.ui.theme.AppBlack
import org.morfly.airin.sample.imagelist.ImageListEntry


@Composable
fun BottomMenuBar(navController: NavController, destinations: FeatureEntries) {
    BottomNavigationLayout {
        GlowingMenuIcon(
            isGlowing = true,
            glowingIcon = Icons.Rounded.Home,
            idleIcon = Icons.Outlined.Home,
            modifier = Modifier
                .clickable {
                    val route = destinations.route<ImageListEntry>()
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
        )
        GlowingMenuIcon(
            isGlowing = false,
            glowingIcon = Icons.Rounded.Favorite,
            idleIcon = Icons.Outlined.FavoriteBorder,
            modifier = Modifier.clickable { }
        )
        Box(contentAlignment = Alignment.Center) {
            GlowingMenuIcon(
                isGlowing = false,
                glowingIcon = Icons.Rounded.Add,
                idleIcon = Icons.Outlined.Add,
                modifier = Modifier.clickable { }
            )
            Box(
                Modifier
                    .aspectRatio(1f)
                    .padding(15.dp)
                    .fillMaxSize()
                    .border(
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        shape = RoundedCornerShape(percent = 50)
                    )
            )
        }
        GlowingMenuIcon(
            isGlowing = false,
            glowingIcon = Icons.Rounded.Person,
            idleIcon = Icons.Outlined.Person,
            modifier = Modifier.clickable { }
        )
        GlowingMenuIcon(
            isGlowing = false,
            glowingIcon = Icons.Rounded.Settings,
            idleIcon = Icons.Outlined.Settings,
            modifier = Modifier.clickable { }
        )
    }
}

@Composable
private inline fun BottomNavigationLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .height(70.dp)
            .glow(AppBlack, radius = 20.dp, alpha = 0.9f, offsetY = 10.dp)
            .clip(RoundedCornerShape(topStartPercent = 40, topEndPercent = 40))
            .background(AppBlack),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}