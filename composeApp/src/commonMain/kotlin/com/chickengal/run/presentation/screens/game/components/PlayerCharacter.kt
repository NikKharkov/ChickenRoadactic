package com.chickengal.run.presentation.screens.game.components

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.character1
import chickenroadactic.composeapp.generated.resources.character2
import chickenroadactic.composeapp.generated.resources.character3
import chickenroadactic.composeapp.generated.resources.character4
import chickenroadactic.composeapp.generated.resources.character5
import chickenroadactic.composeapp.generated.resources.character6
import chickenroadactic.composeapp.generated.resources.character7
import chickenroadactic.composeapp.generated.resources.character8
import chickenroadactic.composeapp.generated.resources.character9
import com.chickengal.run.domain.game.GameState
import com.chickengal.run.domain.game.Player
import com.chickengal.run.domain.shop.SkinType
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerCharacter(
    player: Player,
    gameState: GameState,
    currentSkin: SkinType,
    modifier: Modifier = Modifier
) {
    val swayAnimation by rememberInfiniteTransition().animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedY by animateFloatAsState(
        targetValue = player.y,
        animationSpec = tween(150, easing = EaseOutQuart)
    )

    val characterImage = when (currentSkin) {
        SkinType.SKIN_1 -> Res.drawable.character1
        SkinType.SKIN_2 -> Res.drawable.character2
        SkinType.SKIN_3 -> Res.drawable.character3
        SkinType.SKIN_4 -> Res.drawable.character4
        SkinType.SKIN_5 -> Res.drawable.character5
        SkinType.SKIN_6 -> Res.drawable.character6
        SkinType.SKIN_7 -> Res.drawable.character7
        SkinType.SKIN_8 -> Res.drawable.character8
        SkinType.SKIN_9 -> Res.drawable.character9
    }

    Box(
        modifier = modifier
            .size(player.size.dp, player.height.dp)
            .offset(x = player.x.dp, y = animatedY.dp)
            .graphicsLayer {
                rotationZ = if (gameState == GameState.PLAYING) swayAnimation else 0f
            }
    ) {
        Image(
            painter = painterResource(characterImage),
            contentDescription = "Player",
            modifier = Modifier.fillMaxSize()
        )
    }
}