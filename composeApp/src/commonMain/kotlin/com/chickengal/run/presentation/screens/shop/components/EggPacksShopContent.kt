package com.chickengal.run.presentation.screens.shop.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chickengal.run.domain.shop.CurrencyPack

@Composable
fun EggPacksShopContent(
    packs: List<CurrencyPack>,
    purchaseModifier: (CurrencyPack) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        packs.forEach { item ->
            EggPackCard(
                pack = item,
                onPurchase = { pack ->
                    purchaseModifier(pack)
                }
            )
        }
    }
}