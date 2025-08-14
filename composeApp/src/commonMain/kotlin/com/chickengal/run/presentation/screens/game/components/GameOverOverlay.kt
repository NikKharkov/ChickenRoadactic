package com.chickengal.run.presentation.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.background
import chickenroadactic.composeapp.generated.resources.btn_menu
import chickenroadactic.composeapp.generated.resources.btn_play_again
import chickenroadactic.composeapp.generated.resources.egg
import chickenroadactic.composeapp.generated.resources.purple_box
import com.chickengal.run.presentation.shared.CustomText
import com.chickengal.run.presentation.shared.InteractiveButton
import com.chickengal.run.presentation.shared.Wallpapers
import org.jetbrains.compose.resources.painterResource

@Composable
fun GameOverOverlay(
    collectedCoins: Int,
    finalScore: Int,
    onRestartClick: () -> Unit,
    onMainMenuClick: () -> Unit
) {
    Wallpapers(backgroundResImage = Res.drawable.background)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomText(
            text = "Game Over",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .width(210.dp)
                .height(140.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.purple_box),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF2A105C), shape = RoundedCornerShape(24.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomText(
                        text = "Points:",
                        fontSize = 16.sp
                    )

                    CustomText(
                        text = "$finalScore",
                        fontSize = 16.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF2A105C), shape = RoundedCornerShape(24.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(Res.drawable.egg),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    CustomText(
                        text = "$collectedCoins",
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        InteractiveButton(
            image = painterResource(Res.drawable.btn_play_again),
            onClick = onRestartClick,
            modifier = Modifier
                .width(120.dp)
                .height(60.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        InteractiveButton(
            image = painterResource(Res.drawable.btn_menu),
            onClick = onMainMenuClick,
            modifier = Modifier
                .width(120.dp)
                .height(60.dp)
        )
    }
}