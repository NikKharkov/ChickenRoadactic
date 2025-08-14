package com.chickengal.run.presentation.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.obstacle1
import chickenroadactic.composeapp.generated.resources.obstacle2
import chickenroadactic.composeapp.generated.resources.obstacle3
import com.chickengal.run.domain.game.Obstacle
import com.chickengal.run.domain.game.ObstacleType
import org.jetbrains.compose.resources.painterResource

@Composable
fun ObstacleItem(
    obstacle: Obstacle,
    modifier: Modifier = Modifier
) {
    val obstacleImage = when (obstacle.type) {
        ObstacleType.TYPE1 -> Res.drawable.obstacle1
        ObstacleType.TYPE2 -> Res.drawable.obstacle2
        ObstacleType.TYPE3 -> Res.drawable.obstacle3
    }

    Image(
        painter = painterResource(obstacleImage),
        contentDescription = "Obstacle",
        modifier = modifier
            .size(obstacle.size.dp)
            .offset(x = obstacle.x.dp, y = obstacle.y.dp)
    )
}
