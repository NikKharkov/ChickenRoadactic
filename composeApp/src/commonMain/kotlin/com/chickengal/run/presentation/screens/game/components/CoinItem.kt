package com.chickengal.run.presentation.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.egg
import com.chickengal.run.domain.game.Coin
import org.jetbrains.compose.resources.painterResource

@Composable
fun CoinItem(
    coin: Coin,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(Res.drawable.egg),
        contentDescription = "Coin",
        modifier = modifier
            .size(coin.size.dp)
            .offset(x = coin.x.dp, y = coin.y.dp)
    )
}