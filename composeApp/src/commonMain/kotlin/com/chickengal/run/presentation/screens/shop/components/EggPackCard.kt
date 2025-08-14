package com.chickengal.run.presentation.screens.shop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import chickenroadactic.composeapp.generated.resources.eggpack_m
import chickenroadactic.composeapp.generated.resources.eggpack_s
import chickenroadactic.composeapp.generated.resources.eggpack_xl
import com.chickengal.run.domain.shop.CurrencyPack
import com.chickengal.run.inapp_purchases.Products
import com.chickengal.run.presentation.shared.InteractiveButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun EggPackCard(
    pack: CurrencyPack,
    onPurchase: (CurrencyPack) -> Unit
) {
    val packImage = when (pack.productId) {
        Products.PACK_SMALL -> Res.drawable.eggpack_s
        Products.PACK_MEDIUM -> Res.drawable.eggpack_m
        Products.PACK_LARGE -> Res.drawable.eggpack_xl
        else -> Res.drawable.eggpack_s
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
                painter = painterResource(packImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(buttonPadding)
            ) {
                InteractiveButton(
                    image = painterResource(Res.drawable.btn_buy),
                    onClick = { onPurchase(pack) },
                    modifier = Modifier
                        .width(buttonWidth)
                        .height(buttonHeight)
                )
            }
        }
    }
}