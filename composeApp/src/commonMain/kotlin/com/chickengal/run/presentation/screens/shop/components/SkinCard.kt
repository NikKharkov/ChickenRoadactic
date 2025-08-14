package com.chickengal.run.presentation.screens.shop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.btn_buy
import chickenroadactic.composeapp.generated.resources.btn_choose
import chickenroadactic.composeapp.generated.resources.shop_chicken1
import chickenroadactic.composeapp.generated.resources.shop_chicken2
import chickenroadactic.composeapp.generated.resources.shop_chicken3
import chickenroadactic.composeapp.generated.resources.shop_chicken4
import chickenroadactic.composeapp.generated.resources.shop_chicken5
import chickenroadactic.composeapp.generated.resources.shop_chicken6
import chickenroadactic.composeapp.generated.resources.shop_chicken7
import chickenroadactic.composeapp.generated.resources.shop_chicken8
import chickenroadactic.composeapp.generated.resources.shop_chicken9
import com.chickengal.run.domain.shop.SkinType
import com.chickengal.run.managers.VibrationManager
import com.chickengal.run.presentation.shared.InteractiveButton
import com.chickengal.run.presentation.theme.Yellow
import org.jetbrains.compose.resources.painterResource

@Composable
fun SkinCard(
    skinType: SkinType,
    price: Int,
    regularCoins: Int,
    isPurchased: Boolean,
    isApplied: Boolean,
    onPurchase: () -> Boolean,
    onApply: () -> Unit
) {
    val skinImage = when (skinType) {
        SkinType.SKIN_1 -> Res.drawable.shop_chicken1
        SkinType.SKIN_2 -> Res.drawable.shop_chicken2
        SkinType.SKIN_3 -> Res.drawable.shop_chicken3
        SkinType.SKIN_4 -> Res.drawable.shop_chicken4
        SkinType.SKIN_5 -> Res.drawable.shop_chicken5
        SkinType.SKIN_6 -> Res.drawable.shop_chicken6
        SkinType.SKIN_7 -> Res.drawable.shop_chicken7
        SkinType.SKIN_8 -> Res.drawable.shop_chicken8
        SkinType.SKIN_9 -> Res.drawable.shop_chicken9
    }

    BoxWithConstraints {
        val isSmallScreen = maxHeight < 650.dp
        val cardWidth = if (isSmallScreen) 90.dp else 110.dp
        val cardHeight = if (isSmallScreen) 140.dp else 170.dp
        val buttonWidth = if (isSmallScreen) 65.dp else 80.dp
        val buttonHeight = if (isSmallScreen) 28.dp else 33.dp
        val buttonBottomOffset = if (isSmallScreen) 12.dp else 16.dp
        val borderWidth = if (isSmallScreen) 3.dp else 4.dp

        Box(
            modifier = Modifier.size(width = cardWidth, height = cardHeight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(skinImage),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = borderWidth,
                            brush = if (isApplied) Yellow else SolidColor(Color.Transparent),
                            shape = RoundedCornerShape(12.dp)
                        )
                )

                if (isPurchased) {
                    InteractiveButton(
                        image = painterResource(Res.drawable.btn_choose),
                        onClick = {
                            onApply()
                            VibrationManager.vibrate(100)
                        },
                        modifier = Modifier
                            .size(width = buttonWidth, height = buttonHeight)
                            .align(Alignment.BottomCenter)
                            .offset(y = -buttonBottomOffset)
                    )
                } else {
                    val canAfford = regularCoins >= price

                    InteractiveButton(
                        image = painterResource(Res.drawable.btn_buy),
                        onClick = {
                            onPurchase()
                            VibrationManager.vibrate(100)
                        },
                        enabled = canAfford,
                        modifier = Modifier
                            .size(width = buttonWidth, height = buttonHeight)
                            .align(Alignment.BottomCenter)
                            .offset(y = -buttonBottomOffset)
                    )
                }
            }
        }
    }
}
