package com.chickengal.run.presentation.screens.shop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.btn_buy
import chickenroadactic.composeapp.generated.resources.modifier_laser_blast
import chickenroadactic.composeapp.generated.resources.modifier_photon_pulse
import chickenroadactic.composeapp.generated.resources.modifier_warp_dash
import com.chickengal.run.domain.game.ModifierType
import com.chickengal.run.presentation.shared.InteractiveButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun ModifierCard(
    modifierType: ModifierType,
    onPurchase: () -> Boolean
) {
    val modifierImage = when (modifierType) {
        ModifierType.LASER_BLAST -> Res.drawable.modifier_laser_blast
        ModifierType.PHOTON_PULSE -> Res.drawable.modifier_photon_pulse
        ModifierType.WARP_DASH -> Res.drawable.modifier_warp_dash
    }

    BoxWithConstraints {
        val isSmallScreen = maxHeight < 650.dp
        val cardHeight = if (isSmallScreen) 110.dp else 140.dp
        val buttonWidth = if (isSmallScreen) 55.dp else 70.dp
        val buttonHeight = if (isSmallScreen) 26.dp else 32.dp
        val buttonPadding = if (isSmallScreen) 8.dp else 12.dp

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
        ) {
            Image(
                painter = painterResource(modifierImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(buttonPadding)
                    .let { modifier ->
                        if (isSmallScreen) {
                            modifier.padding(4.dp)
                        } else {
                            modifier
                        }
                    }
            ) {
                InteractiveButton(
                    image = painterResource(Res.drawable.btn_buy),
                    onClick = {
                        onPurchase()
                    },
                    modifier = Modifier
                        .width(buttonWidth)
                        .height(buttonHeight)
                        .let { modifier ->
                            if (isSmallScreen) {
                                modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                            } else {
                                modifier
                            }
                        }
                )
            }
        }
    }
}